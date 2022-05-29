package Networking.Peer;

import Networking.Messages.EncryptedMessage;
import Networking.Messages.Message;

import java.io.IOException;
import java.net.Socket;

public class Peer {
    private Socket peerSocket;
    private String peerIP;
    private Integer peerID;

    public Peer(Socket peer_socket, String peer_ip, Integer userID) {
        this.peerSocket = peer_socket;
        this.peerIP = peer_ip;
        this.peerID = userID;
    }

    public Socket getPeerSocket() {
        return peerSocket;
    }

    public String getPeerIP() {
        return peerIP;
    }

    public int getPeerID(){return peerID;}

    public void sendEncryptedMessage(Message msg) throws IOException {
        peerSocket.getOutputStream().write(EncryptedMessage.encrypt(msg).toPacket());
    }

    @Override
    public String toString() {
        return "Peer{" +
                "peerSocket=" + peerSocket +
                ", peerIP='" + peerIP + '\'' +
                ", peerID=" + peerID +
                '}';
    }
}
