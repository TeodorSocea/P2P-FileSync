package Networking.Swarm;

import Networking.Messages.ConnectAcceptMessage;
import Networking.Messages.ConnectMessage;
import Networking.Messages.MessageHeader;
import Networking.Messages.ParseableMessage;
import Networking.Peer.Peer;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Swarm {
    private Map<Integer, Peer> peers;
    private Integer selfID;
    private String directory;

    private int swarmID;


    public Swarm(int swarmID){
        this.swarmID = swarmID;
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

    public void handleMessage(ParseableMessage msg, Socket source) throws IOException {
        switch (msg.getHeader()){
            case MessageHeader.NEW_CONNECTION_REQUEST: {
                int newID = (int) (Math.random() * Integer.MAX_VALUE);
                ConnectAcceptMessage response = new ConnectAcceptMessage(swarmID, newID);
                peers.put(newID, new Peer(source, source.getInetAddress().toString(), newID));
                peers.get(newID).getPeerSocket().getOutputStream().write(response.toPacket());
            }
            case MessageHeader.NEW_CONNECTION_RESPONSE: {
                ConnectAcceptMessage received = new ConnectAcceptMessage(msg.getRawMessage());
                selfID
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Swarm with ID ");
        s.append(this.swarmID).append(":");

        for (Integer p: this.peers.keySet()) {
            s.append("\npeer with ID ").append(p).append(" and IP ").append(peers.get(p).getPeerIP());
        }
        return s.toString();
    }
}
