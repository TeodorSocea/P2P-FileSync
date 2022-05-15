package Networking.Core;

import Networking.CheckoutLAN.BroadcastReceiver;
import Networking.CheckoutLAN.BroadcastSender;
import Networking.Messages.*;
import Networking.Networking.NetworkManager;
import Networking.Peer.Peer;
import Networking.Swarm.NetworkSwarm;
import Networking.Swarm.NetworkSwarmManager;
import Networking.Utils.DataBuffer;
import Networking.Utils.Invitation;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.*;


public class NetworkingComponent {

    private NetworkSwarmManager networkSwarmManager;
    private NetworkManager networkManager;
    private BroadcastSender broadcastSender;
    private BroadcastReceiver broadcastReceiver;
    private Map<SwarmDataMessage, DataPipeline> swarmDataMessageDataPipelineMap; /* Written by Teodora */
    private int port;
    private int UDP_PORT = 10101;

    public NetworkingComponent(int port){
        try {
            this.port = port;

            networkSwarmManager = new NetworkSwarmManager();
            networkManager = new NetworkManager(port, networkSwarmManager);
            swarmDataMessageDataPipelineMap = new HashMap<>(); /* Written by Teodora */

            //swarmManager = new SwarmManager(port);
            //trafficHandler = new IncomingTrafficHandler(this, port);

            broadcastSender = new BroadcastSender(UDP_PORT,10);
            broadcastReceiver = new BroadcastReceiver(UDP_PORT,10);

            broadcastReceiver.startListening();
            broadcastSender.startBroadcast();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<SwarmDataMessage, DataPipeline> getSwarmDataMessageDataPipelineMap() {
        return swarmDataMessageDataPipelineMap;
    } /* Written by Teodora */

    public void printCommonIPPool(){
        System.out.println(broadcastReceiver.getIpSet());
    }

    public List<String> getCommonIPPool(){
        return broadcastReceiver.getIpSet();
    }

    public void printCommonSocketPool(){
        networkManager.getCommonSocketPool();
    }

    public List<Socket> getCommonSocketPool(){
        return networkManager.getCommonSocketPool();
    }

    public void connectToIP(String ip) throws IOException {
        networkManager.connectToIP(ip);
    }

    public void createNewSwarm(){
        networkSwarmManager.createNewSwarm();
    }

    public void printSwarms(){
        System.out.println(networkSwarmManager.getSwarms());
    }

    public Map<Integer, NetworkSwarm> getSwarms(){
        return networkSwarmManager.getSwarms();
    }

    public void printInvitations(){
        System.out.println(networkSwarmManager.getInvitations());
    }

    public List<Invitation> getInvitations(){
        return networkSwarmManager.getInvitations();
    }

    public void inviteIPToSwarm(String ip, int swarmID) throws IOException {
        networkManager.inviteIPToSwarm(ip, networkSwarmManager.getSwarms().get(swarmID));
    }

    public void respondToInvitationToSwarm(int index, boolean response) throws IOException {
        Invitation invitation = networkSwarmManager.getInvitations().get(index);

        if(response == true) {
            networkSwarmManager.joinNewSwarm(invitation.getSwarmID(), invitation.getSelfID());
            Peer sender = new Peer(invitation.getSocket(), invitation.getSocket().getRemoteSocketAddress().toString(), invitation.getSenderID());
            networkSwarmManager.addPeerToSwarm(invitation.getSwarmID(), sender);
            InviteResponseMessage responseMessage = new InviteResponseMessage(MessageHeader.RESPONSE_INVITE_TO_SWARM, invitation.getSelfID(), invitation.getSwarmID(), true);
            invitation.getSocket().getOutputStream().write(responseMessage.toPacket());
        }else{
            InviteResponseMessage responseMessage = new InviteResponseMessage(MessageHeader.RESPONSE_INVITE_TO_SWARM, invitation.getSelfID(), invitation.getSwarmID(), false);
            invitation.getSocket().getOutputStream().write(responseMessage.toPacket());
        }

        networkSwarmManager.getInvitations().remove(invitation);
    }

    public void sendDataToPeers(byte[] data, int swarmID) throws IOException {
        NetworkSwarm swarm = networkSwarmManager.getSwarms().get(swarmID);
        for(int i = 0 ; i < data.length / 1024 + 1; i++){
            byte[] dataToSend = Arrays.copyOfRange(data, i * 1024, Math.min((i+1) * 1024, data.length));
            DataMessage dataMessage = new DataMessage(MessageHeader.DATA, swarm.getSelfID(), swarmID, i, dataToSend);
            for(Map.Entry<Integer, Peer> entry : swarm.getPeers().entrySet()){
                entry.getValue().getPeerSocket().getOutputStream().write(dataMessage.toPacket());
            }
        }
    }

    public Map<Integer, byte[]> receiveData(int swarmID){
        Map<Integer, byte[]> output = new HashMap<>();
        Map<Integer, DataBuffer> dataBufferMap = networkSwarmManager.getIncomingData(swarmID);
        for(Map.Entry<Integer, DataBuffer> entry : dataBufferMap.entrySet()){
            DataBuffer dataBuffer = entry.getValue();
            int max = Collections.max(dataBuffer.getDataMap().keySet());
            ByteBuffer byteBuffer = ByteBuffer.allocate((max+1)*1024);
            for(int chunkID : dataBuffer.getDataMap().keySet()){
                byteBuffer.put(chunkID * 1024, dataBuffer.getDataMap().get(chunkID));
            }
            output.put(entry.getKey(), byteBuffer.array());
        }
        return output;
    }

    public void requestChunk(int fileID, int chunkID, int swarmID, int userID){  //fileID might be replaced by a string containing the path or sth
        Peer peer = networkSwarmManager.getSwarms().get(swarmID).getPeers().get(userID);
        DataMessageRequest request = new DataMessageRequest(userID,swarmID,fileID,chunkID);
        try {
            peer.getPeerSocket().getOutputStream().write(request.toPacket());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendChunk(int fileID, int chunkID, int swarmID, int userID, byte[] data){
        Peer peer = networkSwarmManager.getSwarms().get(swarmID).getPeers().get(userID);
        DataMessage msg = new DataMessage(MessageHeader.DATA,userID,swarmID,chunkID,data);
        try {
            peer.getPeerSocket().getOutputStream().write(msg.toPacket());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] receiveChunk(DataMessage message){
        return message.getData().clone(); //maybe send it to daemon?
    }



    public String getSelfIp() throws UnknownHostException {
       return InetAddress.getLocalHost().getHostAddress();
       //need changes on linux doesn't work return 127.0.0.1
       //it may be because of file in /etc/hosts
       //I will do an iteration over all network interfaces and return a good one
    }


}
