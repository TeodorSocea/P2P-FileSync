package Networking.Core;

import Networking.CheckoutLAN.BroadcastReceiver;
import Networking.CheckoutLAN.BroadcastSender;
import Networking.Messages.*;
import Networking.Networking.NetworkManager;
import Networking.Peer.Peer;
import Networking.Swarm.NetworkSwarm;
import Networking.Swarm.NetworkSwarmManager;
import Networking.Utils.*;
import javafx.util.Pair;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class NetworkingComponent {

    private NetworkSwarmManager networkSwarmManager;
    private Map<Integer, NetworkSwarm> definitiveNetworkSwarms;
    private NetworkManager networkManager;
    private BroadcastSender broadcastSender;
    private BroadcastReceiver broadcastReceiver;
    private Map<Integer, DataPipeline> dataPipelineMap;
    private int port;
    private int UDP_PORT = 10101;
    // private Config config = Config.getInstance(); soon

    public NetworkingComponent(int port){
        try {
            this.port = port;

            networkSwarmManager = new NetworkSwarmManager();
            dataPipelineMap = new HashMap<>();
            networkManager = new NetworkManager(port, networkSwarmManager, dataPipelineMap);

            broadcastSender = new BroadcastSender(UDP_PORT,10);
            broadcastReceiver = new BroadcastReceiver(UDP_PORT,10);

            broadcastReceiver.startListening();
            broadcastSender.startBroadcast();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, DataPipeline> getDataPipelineMap() {
        return dataPipelineMap;
    }

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

    public int createNewSwarm(){
        return networkSwarmManager.createNewSwarm();
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

    public List<Triple<Integer, Integer, String>> getRequests(){
        List<Triple<Integer, Integer, String>> output = new ArrayList<>();
        for(Map.Entry<Integer, NetworkSwarm> swarm : networkSwarmManager.getSwarms().entrySet()){
            for(Pair<Integer, String> request : swarm.getValue().getRequests()){
//                System.out.println("Peer " + request.getKey() + " in swarm " + swarm.getKey() + " wants " + request.getValue());
                output.add(new MutableTriple<Integer, Integer, String>(swarm.getKey(),request.getKey(),request.getValue()));
            }
            swarm.getValue().setRequests(new ArrayList<Pair<Integer, String>>());
        }

        return output;
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
    // will soon be deprecated
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

    public void sentDataToPeer(byte[] data, int swarmID, int peerID) throws IOException {
        String EOF = "NET_EOF_MARKER";
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length + EOF.length());
        byteBuffer.put(data);
        byteBuffer.put(EOF.getBytes(StandardCharsets.UTF_8));
        data = byteBuffer.array();
        Peer peer = networkSwarmManager.getSwarms().get(swarmID).getPeers().get(peerID);
        for(int i = 0; i < data.length / 1024 + 1 ; i++){
            byte[] dataToSend = Arrays.copyOfRange(data, i * 1024, Math.min((i+1) * 1024, data.length));
            DataMessage dataMessage = new DataMessage(MessageHeader.DATA, networkSwarmManager.getSwarms().get(swarmID).getSelfID(), swarmID, i, dataToSend);
            peer.getPeerSocket().getOutputStream().write(dataMessage.toPacket());
        }
        DataMessage dataMessage = new DataMessage(MessageHeader.DATA, networkSwarmManager.getSwarms().get(swarmID).getSelfID(), swarmID, -1, new byte[1024]);
        peer.getPeerSocket().getOutputStream().write(dataMessage.toPacket());
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

    public int indexOf(byte[] outerArray, byte[] smallerArray) {
        for(int i = 0; i < outerArray.length - smallerArray.length+1; ++i) {
            boolean found = true;
            for(int j = 0; j < smallerArray.length; ++j) {
                if (outerArray[i+j] != smallerArray[j]) {
                    found = false;
                    break;
                }
            }
            if (found) return i;
        }
        return -1;
    }

    public byte[] getDataFromDataPipeline(int swarmID, int peerID){
        List<Data> allDataFromPeer = dataPipelineMap.get(swarmID).getDataInPipeline(peerID);
        Data data = allDataFromPeer.remove(0);
        dataPipelineMap.get(swarmID).updateLatestIndexOfPeer(peerID, false);
        int allocationSize = data.getData().size()*1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate(allocationSize);
        int index = 0;
        for(byte[] byteArray : data.getData()){
            byteBuffer.put(index * 1024, byteArray);
            index++;
        }
        String EOF = "NET_EOF_MARKER";
        int indexOfEOF = indexOf(byteBuffer.array(), EOF.getBytes(StandardCharsets.UTF_8));
        networkSwarmManager.getSwarms().get(swarmID).popFulfilledRequests(peerID);
        ByteBuffer auxByteBuffer = ByteBuffer.allocate(indexOfEOF);
        auxByteBuffer.put(byteBuffer.array(), 0, indexOfEOF);
        return auxByteBuffer.array();
    }

    public int getLatestIndex(int swarmID, int peerID){
        return dataPipelineMap.get(swarmID).getLatestIndexOfPeer(peerID);
    }

    public String getSelfIp() throws UnknownHostException {
        try {
            return LanIP.getLanIP();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void requestDataFromSwarm(int swarmID, int peerID, String path) throws IOException {
        DataRequestMessage dataRequestMessage = new DataRequestMessage(MessageHeader.DATA_REQUEST, networkSwarmManager.getSwarms().get(swarmID).getSelfID(), swarmID, path);
        networkSwarmManager.getSwarms().get(swarmID).getPeers().get(peerID).getPeerSocket().getOutputStream().write(dataRequestMessage.toPacket());
    }

    public List<Pair<Integer, Integer>> getFulfilledRequests(){
        List<Pair<Integer, Integer>> output = new ArrayList<>();
        for(Map.Entry<Integer, NetworkSwarm> swarm : networkSwarmManager.getSwarms().entrySet()){
            if (swarm.getValue().getFulfilledRequests().size() == 0)
                continue;
            for (int peerID : swarm.getValue().getFulfilledRequests()){
                output.add(new Pair<Integer, Integer>(swarm.getKey(), peerID));
            }
        }
//        System.out.println(output);
        return output;
    }
}
