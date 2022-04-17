package Networking.Swarm;

import Networking.Messages.MessageHeader;
import Networking.Messages.ParseableMessage;
import Networking.Messages.RequestPeersMessage;
import Networking.Messages.ResponsePeersMessage;
import Networking.Peer.Peer;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Swarm {
    private Map<Integer, Peer> peers;
    private Integer selfID;
    private String directory;
    private int port;

    private int swarmID;


    public Swarm(int swarmID, int port){
        this.swarmID = swarmID;
        peers = new HashMap<>();
        directory = "files/"; //temporary
        this.port = port;
    }

    public void setSelfID(Integer selfID) {
        this.selfID = selfID;
    }

    public int getSelfID() {
        return this.selfID;
    }

    public Map<Integer, Peer> getPeers(){
        return peers;
    }

    public void addPeer(String ip, Socket peerSocket, Integer userID) throws IOException {
        Peer peer_obj = new Peer(peerSocket, ip, userID);
        peers.put(userID, peer_obj);
    }

    public void handleMessage(ParseableMessage msg, Socket source) throws IOException {
        switch (msg.getHeader()){
            case MessageHeader.PEER_REQUEST : {
                RequestPeersMessage received = new RequestPeersMessage(msg.getRawMessage());
                ResponsePeersMessage response = new ResponsePeersMessage(swarmID, selfID, peers);
            }
            case MessageHeader.PEER_RESPONSE: {
                ResponsePeersMessage received = new ResponsePeersMessage(msg.getRawMessage());

                byte[][] ipsAsArray = received.getIpsAsArray();
                int[] peerIDs = received.getPeerIDs();
                for(int i = 0 ; i < received.getIpsNumber(); i++) {
                    String ip = (ipsAsArray[i][0] & 0xFF) + "." +
                                (ipsAsArray[i][1] & 0xFF) + "." +
                                (ipsAsArray[i][2] & 0xFF) + "." +
                                (ipsAsArray[i][3] & 0xFF);
                    int peerID = peerIDs[i];
                    if(!peers.containsKey(peerID)){
                        Socket peer_socket = new Socket(ip, port);
                        String peer_ip = peer_socket.getInetAddress().toString();
                        peers.put(peerID, new Peer(peer_socket, peer_ip, peerID));
                        System.out.println(peers.get(peerID));
                    }
                }
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

    public int getPort(){
        return this.port;
    }
}
