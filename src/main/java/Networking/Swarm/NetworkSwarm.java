package Networking.Swarm;

import Networking.Utils.DataBuffer;
import Networking.Peer.Peer;
import javafx.util.Pair;

import java.util.*;

public class NetworkSwarm {

    private int MAX = 1000;

    // When creating or joining a swarm, the agent will have to know its own id

    private int swarmID;
    private int selfID;
    private String swarmName;
    private Map<Integer, Peer> peers;
    private Map<Integer, DataBuffer> dataBufferMap;
    private List<Pair<Integer, String>> requests;
    private List<Integer> fulfilledRequests;

    public NetworkSwarm(int swarmID, int selfID){
        this.swarmID = swarmID;
        this.selfID = selfID;
        this.peers = new HashMap<>();
        this.dataBufferMap = new HashMap<>();
        this.requests = new ArrayList<>();
        this.fulfilledRequests = new ArrayList<>();
    }

    public List<Pair<Integer, String>> getRequests() {
        return requests;
    }

    public List<Integer> getFulfilledRequests() {
        return fulfilledRequests;
    }

    public int getSwarmID() {
        return swarmID;
    }

    public int getSelfID() {
        return selfID;
    }

    public Map<Integer, Peer> getPeers() {
        return peers;
    }

    public Map<Integer, DataBuffer> getDataBufferMap() {
        return dataBufferMap;
    }

    public int generateNextID(){
        Random rand = new Random();
        int nextID;
        do{
            nextID = rand.nextInt(MAX);
        }while(peers.containsKey(nextID));
        return nextID;
    }

    public void addNewPeer(Peer peer){
        peers.put(peer.getPeerID(), peer);
    }

    public void addData(int senderID, int chunkID, byte[] data){
        if(!dataBufferMap.containsKey(senderID)) {
            dataBufferMap.put(senderID, new DataBuffer());
        }
        dataBufferMap.get(senderID).addToData(chunkID, data);
    }

    @Override
    public String toString() {
        return "NetworkSwarm{" +
                "MAX=" + MAX +
                ", swarmID=" + swarmID +
                ", selfID=" + selfID +
                ", swarmName='" + swarmName + '\'' +
                ", peers=" + peers +
                '}';
    }
}
