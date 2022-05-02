package Networking.Core;

import Networking.Peer.Peer;
import Networking.Swarm.Swarm;

import java.net.Socket;
import java.util.*;

public class NetworkSwarmManager {
    private int MAX = 1000;

    private Map<Integer, NetworkSwarm> swarms;
    private List<Invitation> invitations;


    public NetworkSwarmManager(){
        this.swarms = new HashMap<>();
        this.invitations = new ArrayList<>();
    }

    // we can add parameters to specify a folder for example
    public void createNewSwarm(){
        Random rand = new Random();
        int nextID;
        do{
            nextID = rand.nextInt(MAX);
        }while(swarms.containsKey(nextID));
        int selfID = rand.nextInt(MAX);
        swarms.put(nextID, new NetworkSwarm(nextID,selfID));
        System.out.println("Created swarm " + nextID);
    }

    public void joinNewSwarm(int swarmID, int selfID){
        swarms.put(swarmID, new NetworkSwarm(swarmID, selfID));
    }

    public void addPeerToSwarm(int swarmID, Peer peer){
        swarms.get(swarmID).addNewPeer(peer);
    }

    public void addInvitation(Invitation invitation){
        invitations.add(invitation);
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public Map<Integer, DataBuffer> getIncomingData(int swarmID){
        return swarms.get(swarmID).getDataBufferMap();
    }

    public Map<Integer, NetworkSwarm> getSwarms(){
        return swarms;
    }
}
