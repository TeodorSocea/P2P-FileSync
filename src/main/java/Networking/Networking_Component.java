package Networking;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Networking_Component {
    private List<Swarm> Swarms;

    public void connect(String ip) throws IOException {
        int port = 32000;
        Socket connection = new Socket(ip, port);
        while(true){

        }
    }

    private String[] discover(){
        String[] ret = {"ala", "pala"};
        return ret;
    }

    public Networking_Component(){
    }

    public void listen(){
        PeerListener listener = new PeerListener();
        new Thread(listener).start();
    }

    public void stopListen(){}

    public void sendRequest(Byte[] bytes){}

    public AbstractResponse receiveResponse(){
        return new GetDataResponse();
    }
}
