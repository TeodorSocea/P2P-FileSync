package Networking.Networking;

import Networking.Utils.Invitation;
import Networking.Messages.*;
import Networking.Peer.Peer;
import Networking.Swarm.NetworkSwarm;
import Networking.Swarm.NetworkSwarmManager;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ConnectionHandler implements Runnable{

    private Socket selfSocket;
    private NetworkSwarmManager networkSwarmManager;
    private List<Socket> commonSocketPool;

    public ConnectionHandler(Socket selfSocket, NetworkSwarmManager networkSwarmManager, List<Socket> commonSocketPool){
        this.selfSocket = selfSocket;
        this.networkSwarmManager = networkSwarmManager;
        this.commonSocketPool = commonSocketPool;
    }

    private Message readIncomingMessage() throws IOException {
        byte[] messageSize = new byte[4];
        selfSocket.getInputStream().read(messageSize);
        int definitiveMessageSize = Messages.getIntFromByteArray(messageSize, 0);
        byte[] buf = new byte[definitiveMessageSize];
        int readBytes = selfSocket.getInputStream().read(buf);
        byte[] rawMessage = ArrayUtils.addAll(messageSize, buf);
        Message incoming = new Message(Arrays.copyOfRange(rawMessage, 0, definitiveMessageSize));

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
            sb.append(Integer.toString(b));
        }
        return sb.toString();
    }

    private void sendSwarmData(int swarmID, int senderID) throws IOException {
        NetworkSwarm swarm = networkSwarmManager.getSwarms().get(swarmID);
        for(Map.Entry<Integer, Peer> entry : swarm.getPeers().entrySet()){
            SwarmDataMessage swarmDataMessage = new SwarmDataMessage(MessageHeader.SWARM_DATA, senderID, entry.getValue());
            selfSocket.getOutputStream().write(swarmDataMessage.toPacket());
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                Message incoming = readIncomingMessage();
                switch (incoming.getHeader()) {
                    case MessageHeader.INVITE_TO_SWARM -> {
                        InviteMessage invitationMessage = new InviteMessage(incoming.getRawMessage());
                        Invitation invitation = new Invitation(invitationMessage.getSenderID(), invitationMessage.getSwarmID(), invitationMessage.getInviteeID(), selfSocket);
                        networkSwarmManager.addInvitation(invitation);
                        break;
                    }
                    case MessageHeader.RESPONSE_INVITE_TO_SWARM -> {
                        InviteResponseMessage responseMessage = new InviteResponseMessage(incoming.getRawMessage());
                        if(responseMessage.getResponse() == 1){
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

                        if(newSocket == null) {
                            newSocket = new Socket(ip, selfSocket.getPort());

                            commonSocketPool.add(newSocket);

                            ConnectionHandler connectionHandler = new ConnectionHandler(newSocket, networkSwarmManager, commonSocketPool);

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
                    }
                    case MessageHeader.DATA -> {
                        DataMessage dataMessage = new DataMessage(incoming.getRawMessage());
                        networkSwarmManager.getSwarms().get(dataMessage.getSwarmID()).addData(dataMessage.getSenderID(), dataMessage.getChunkID(), dataMessage.getData());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}