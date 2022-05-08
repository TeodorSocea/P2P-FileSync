package Networking.Swarm;

import Networking.Utils.DataBuffer;
import Networking.Peer.Peer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NetworkSwarm {

    private int MAX = 1000;

    // When creating or joining a swarm, the agent will have to know its own id

    private int swarmID;
    private int selfID;
    private String swarmName;
    private Map<Integer, Peer> peers;
    private Map<Integer, DataBuffer> dataBufferMap;

    public NetworkSwarm(int swarmID, int selfID){
        this.swarmID = swarmID;
        this.selfID = selfID;
        this.peers = new HashMap<>();
        this.dataBufferMap = new HashMap<>();
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
