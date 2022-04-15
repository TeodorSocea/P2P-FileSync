package Networking.Swarm;

import Networking.Messages.MessageHeader;
import Networking.Messages.ParseableMessage;
import Networking.Peer.Peer;
import Networking.Peer.PeerHandler;
import Networking.Peer.PeerListener;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Swarm {
    private Map<Integer, Peer> peers;
    private Integer selfID;
    private String directory;

    private int id;


    public Swarm(int id){
        this.id = id;
        peers = new HashMap<>();
        directory = "files/"; //temporary
    }

    public void setSelfID(Integer selfID) {
        this.selfID = selfID;
    }

    public Map<Integer, Peer> getPeers(){
        return peers;
    }

    public void addPeer(String ip, Socket peerSocket, Integer userID) throws IOException {
        Peer peer_obj = new Peer(peerSocket, ip, userID);
        peers.put(userID, peer_obj);
    }

    public void closePeer(){
        // TODO
    }

    public void handleMessage(ParseableMessage msg){
        if(msg.getHeader() == MessageHeader.NEW_CONNECTION_REQUEST){

        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Swarm with ID ");
        s.append(this.id).append(":");

        for (Integer p: this.peers.keySet()) {
            s.append("\npeer with ID ").append(p).append(" and IP ").append(peers.get(p).getPeerIP());
        }
        return s.toString();
    }
}
