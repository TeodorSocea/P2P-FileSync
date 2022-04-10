package Networking.Peer;

import Networking.Peer.Peer;
import Networking.Peer.PeerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PeerListener implements Runnable{
    private ServerSocket listener;
    private int port;
    private Map<String, Peer> peers;
    private Map<Peer, PeerHandler> peerHandlers;

    public PeerListener(int port){
        peers = new HashMap<String, Peer>();
        peerHandlers = new HashMap<Peer, PeerHandler>();
        this.port = port;
    }

    public Map<String, Peer> getPeers() {
        return peers;
    }

    public Map<Peer, PeerHandler> getPeerHandlers() {
        return peerHandlers;
    }

    @Override
    public void run() {
        try {
            listener = new ServerSocket(port);
            listener.setReuseAddress(true);
            while(true){
                Socket peer_socket = listener.accept();
                String peer_ip = peer_socket.getRemoteSocketAddress().toString();
                Peer peer_obj = new Peer(peer_socket, peer_ip);
                System.out.println(peer_ip);
                peers.put(peer_ip, peer_obj);
                peerHandlers.put(peer_obj, new PeerHandler(peer_obj));
                new Thread(peerHandlers.get(peers.size() - 1)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(listener != null){
                try {
                    listener.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
