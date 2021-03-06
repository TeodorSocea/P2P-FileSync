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


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

    public int createNewSwarmName(String name){
        return networkSwarmManager.createNewSwarm(name);
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
            networkSwarmManager.joinNewSwarm(invitation.getSwarmID(), invitation.getSelfID(), invitation.getSwarmName());
            Peer sender = new Peer(invitation.getSocket(), invitation.getSocket().getRemoteSocketAddress().toString(), invitation.getSenderID());
            networkSwarmManager.addPeerToSwarm(invitation.getSwarmID(), sender);
            InviteResponseMessage responseMessage = new InviteResponseMessage(MessageHeader.RESPONSE_INVITE_TO_SWARM, invitation.getSelfID(), invitation.getSwarmID(), true);
            sender.getPeerSocket().getOutputStream().write(responseMessage.toPacket());
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
        int EOF = data.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + data.length);
        byteBuffer.putInt(EOF);
        byteBuffer.put(data);
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

    public byte[] getDataFromDataPipeline(int swarmID, int peerID){
        List<Data> allDataFromPeer = dataPipelineMap.get(swarmID).getDataInPipeline(peerID);
        Data data = allDataFromPeer.remove(0);
        dataPipelineMap.get(swarmID).updateLatestIndexOfPeer(peerID, false);
        int allocationSize = Messages.getIntFromByteArray(data.getData().get(0), 0);
        ByteBuffer byteBuffer = ByteBuffer.allocate(allocationSize);
        int index = 0;
        int mod = 1024;

        for(byte[] byteArray : data.getData()){
            if((index + 1) * 1024 > allocationSize){
                mod = 1024 - ((index + 1) * 1024 - allocationSize);
            }
            if (index == 0){
                byteBuffer.put(Arrays.copyOfRange(byteArray, 4, Math.min( 4 + mod, byteArray.length)));
            }
            else{
                byteBuffer.put(Arrays.copyOfRange(byteArray, 0, Math.min(mod, byteArray.length)));
            }
            index++;
        }
        networkSwarmManager.getSwarms().get(swarmID).popFulfilledRequests(peerID);


        return byteBuffer.array();
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
