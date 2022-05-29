package Networking.Networking;

import Networking.Utils.DataPipeline;
import Networking.Utils.Invitation;
import Networking.Messages.*;
import Networking.Peer.Peer;
import Networking.Swarm.NetworkSwarm;
import Networking.Swarm.NetworkSwarmManager;
import javafx.util.Pair;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.*;

public class ConnectionHandler implements Runnable{

    private Socket selfSocket;
    private NetworkSwarmManager networkSwarmManager;
    private List<Socket> commonSocketPool;
    private Map<Integer, DataPipeline> dataPipelineMap;

    public ConnectionHandler(Socket selfSocket, NetworkSwarmManager networkSwarmManager, List<Socket> commonSocketPool, Map<Integer, DataPipeline> dataPipelineMap){
        this.selfSocket = selfSocket;
        this.networkSwarmManager = networkSwarmManager;
        this.commonSocketPool = commonSocketPool;
        this.dataPipelineMap = dataPipelineMap;
    }

    private Message readIncomingMessage() throws IOException {
        byte[] messageSize = new byte[4];
        int definitiveMessageSize;
        do{
            selfSocket.getInputStream().read(messageSize);
            definitiveMessageSize = Messages.getIntFromByteArray(messageSize, 0);
        }while(definitiveMessageSize == 0);
        byte[] buf = new byte[definitiveMessageSize - 4];
        int readBytes = selfSocket.getInputStream().read(buf, 0, buf.length);
        ByteBuffer buffer = ByteBuffer.allocate(definitiveMessageSize);
        buffer.putInt(definitiveMessageSize);
        buffer.put(buf);
        //byte[] rawMessage = ArrayUtils.addAll(messageSize, buf);
        Message incoming = new Message(buffer.array());
        incoming.decrypt();

        return incoming;
    }

    private int getSelfIDFromSwarm(int swarmID){
        NetworkSwarm swarm = networkSwarmManager.getSwarms().get(swarmID);
        for(Map.Entry<Integer, Peer> entry : swarm.getPeers().entrySet()){
            if(entry.getValue().getPeerSocket() == selfSocket){
                return entry.getKey();
            }
        }
        return -1;
    }

    private Socket isInCommonSocketPool(String ip){
        for(Socket socket : commonSocketPool){
            if(Objects.equals(socket.getRemoteSocketAddress().toString(), ip)){
                return socket;
            }
        }
        return null;
    }

    private String inetToString(byte[] ip){
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for(byte b : ip){
            sb.append(prefix);
            prefix = ".";
            sb.append(Integer.toString(b & 0xff));
        }
        return sb.toString();
    }

    private void sendSwarmData(int swarmID, int senderID) throws IOException {
        NetworkSwarm swarm = networkSwarmManager.getSwarms().get(swarmID);
        for(Map.Entry<Integer, Peer> entry : swarm.getPeers().entrySet()){
            SwarmDataMessage swarmDataMessage = new SwarmDataMessage(MessageHeader.SWARM_DATA, senderID, swarmID, entry.getValue());
            selfSocket.getOutputStream().write(swarmDataMessage.toPacket());
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                Message incoming = null;
                try{
                    incoming = readIncomingMessage();
                }catch(SocketException e){
                    for(Map.Entry<Integer, NetworkSwarm> entrySwarm : networkSwarmManager.getSwarms().entrySet()){
                        Iterator<Map.Entry<Integer, Peer>> iter = entrySwarm.getValue().getPeers().entrySet().iterator();
                        while(iter.hasNext()){
                            Map.Entry<Integer, Peer> item = iter.next();
                            if(item.getValue().getPeerSocket() == selfSocket){
                                iter.remove();
                            }
                        }
                    }
                    break;
                }
                switch (incoming.getHeader()) {
                    case MessageHeader.INVITE_TO_SWARM -> {
                        InviteMessage invitationMessage = new InviteMessage(incoming.getRawMessage());
                        Invitation invitation = new Invitation(invitationMessage.getSenderID(), invitationMessage.getSwarmID(), invitationMessage.getInviteeID(), selfSocket);
                        networkSwarmManager.addInvitation(invitation);
                        break;
                    }
                    case MessageHeader.RESPONSE_INVITE_TO_SWARM -> {
                        InviteResponseMessage responseMessage = new InviteResponseMessage(incoming.getRawMessage());
                        if (responseMessage.getResponse() == 1) {
                            Peer sender = new Peer(selfSocket, selfSocket.getRemoteSocketAddress().toString(), responseMessage.getSenderID());
                            sendSwarmData(responseMessage.getSwarmID(), getSelfIDFromSwarm(responseMessage.getSwarmID()));
                            networkSwarmManager.addPeerToSwarm(responseMessage.getSwarmID(), sender);
                        }
                        break;
                    }
                    case MessageHeader.SWARM_DATA -> {
                        SwarmDataMessage swarmDataMessage = new SwarmDataMessage(incoming.getRawMessage());
                        String ip = inetToString(swarmDataMessage.getPeerIP());
                        Socket newSocket = isInCommonSocketPool(ip);

                        if (newSocket == null) {
                            newSocket = new Socket(ip, 30000);

                            commonSocketPool.add(newSocket);

                            ConnectionHandler connectionHandler = new ConnectionHandler(newSocket, networkSwarmManager, commonSocketPool, dataPipelineMap);

                            new Thread(connectionHandler).start();
                        }
                        NewPeerMessage newPeerMessage = new NewPeerMessage(MessageHeader.NEW_PEER, networkSwarmManager.getSwarms().get(swarmDataMessage.getSwarmID()).getSelfID(), swarmDataMessage.getSwarmID());
                        newSocket.getOutputStream().write(newPeerMessage.toPacket());
                        Peer newPeer = new Peer(newSocket, newSocket.getInetAddress().toString(), swarmDataMessage.getPeerID());
                        networkSwarmManager.addPeerToSwarm(swarmDataMessage.getSwarmID(), newPeer);
                        break;
                    }
                    case MessageHeader.NEW_PEER -> {
                        NewPeerMessage newPeerMessage = new NewPeerMessage(incoming.getRawMessage());
                        Peer newPeer = new Peer(selfSocket, selfSocket.getInetAddress().toString(), newPeerMessage.getSenderID());
                        networkSwarmManager.getSwarms().get(newPeerMessage.getSwarmID()).addNewPeer(newPeer);
                        break;
                    }
                    case MessageHeader.DATA -> {
                        DataMessage dataMessage = new DataMessage(incoming.getRawMessage());
                        if (dataMessage.getChunkID() == -1) {
                            dataPipelineMap.get(dataMessage.getSwarmID()).updateLatestIndexOfPeer(dataMessage.getSenderID(), true);
                            networkSwarmManager.getSwarms().get(dataMessage.getSwarmID()).getFulfilledRequests().add(dataMessage.getSenderID());
                            break;
                        }
                        if (!dataPipelineMap.containsKey(dataMessage.getSwarmID())) {
                            dataPipelineMap.put(dataMessage.getSwarmID(), new DataPipeline());
                        }
                        int latestIndex = dataPipelineMap.get(dataMessage.getSwarmID()).getLatestIndexOfPeer(dataMessage.getSenderID());
                        dataPipelineMap.get(dataMessage.getSwarmID()).addData(dataMessage.getSenderID(), dataMessage.getData(), latestIndex);
                        break;
                    }
                    case MessageHeader.DATA_REQUEST -> {
                        DataRequestMessage dataRequestMessage = new DataRequestMessage(incoming.getRawMessage());
                        networkSwarmManager.getSwarms().get(dataRequestMessage.getSwarmID()).getRequests().add(new Pair<Integer, String>(dataRequestMessage.getSenderID(), dataRequestMessage.getPath()));
                        break;
                    }
                }
                System.gc();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
