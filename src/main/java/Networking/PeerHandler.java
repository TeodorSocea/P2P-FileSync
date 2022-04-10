package Networking;

public class PeerHandler implements Runnable{
    private Peer peer;

    public PeerHandler(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        while(true){

        }
    }

    @Override
    public String toString() {
        return "PeerHandler{" +
                "peer=" + peer +
                '}';
    }
}
