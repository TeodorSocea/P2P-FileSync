package Networking;

import java.net.Socket;

public class Peer {
    private Socket peer_socket;
    private String peer_ip;
    public Peer(Socket peer_socket, String peer_ip) {
        this.peer_socket = peer_socket;
        this.peer_ip = peer_ip;
    }

    @Override
    public String toString() {
        return "Peer{" +
                "peer_socket=" + peer_socket +
                ", peer_ip='" + peer_ip + '\'' +
                '}';
    }
}
