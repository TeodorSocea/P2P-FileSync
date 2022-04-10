package Networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PeerListener implements Runnable{
    private ServerSocket listener;
    private Map<String, PeerHandler> peers;

    public PeerListener(){
        peers = new HashMap<String, PeerHandler>();
    }

    public Map<String, PeerHandler> getPeers() {
        return peers;
    }

    @Override
    public void run() {
        try {
            listener = new ServerSocket(32000);
            listener.setReuseAddress(true);
            while(true){
                Socket peer_socket = listener.accept();
                String peer_ip = peer_socket.getRemoteSocketAddress().toString();
                System.out.println(peer_ip);
                peers.put(peer_ip, new PeerHandler(new Peer(peer_socket, peer_ip)));
                new Thread(peers.get(peers.size() - 1)).start();
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
