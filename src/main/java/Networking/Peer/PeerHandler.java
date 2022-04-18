package Networking.Peer;

import Networking.Messages.Messages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class PeerHandler implements Runnable{
    private Peer peer;

    public PeerHandler(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        try {
            InputStream in = peer.getPeerSocket().getInputStream();
            while(true){
                byte[] input = new byte[Messages.MAX_SIZE];
                int count = in.read(input);

                System.out.println(Arrays.toString(input));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "PeerHandler{" +
                "peer=" + peer +
                '}';
    }
}
