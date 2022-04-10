package Networking;

import Networking.Peer.Peer;
import Networking.Peer.PeerHandler;
import Networking.Peer.PeerListener;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class Swarm {
    private PeerListener Known_Hosts;

    public Swarm(int port){
        Known_Hosts = new PeerListener(port);
        new Thread(Known_Hosts).start();
    }

    public Map<String, Peer> getPeers(){
        return Known_Hosts.getPeers();
    }

    public void addPeer(String ip, int port) throws IOException {
        Socket peer_socket = new Socket(ip, port);
        Peer peer_obj = new Peer(peer_socket, ip);
        Known_Hosts.getPeers().put(ip, peer_obj);
        Known_Hosts.getPeerHandlers().put(peer_obj, new PeerHandler(peer_obj));
        new Thread(Known_Hosts.getPeerHandlers().get(peer_obj)).start();
    }

    public void closePeer(){
        // TODO
    }
}
