package Networking;

import java.util.List;

public class Networking_Component {
    private List<Swarm> Swarms;

    private void connect(String ip){}

    private String[] discover(){
        String[] ret = {"ala", "pala"};
        return ret;
    }

    public void listen(){}

    public void stopListen(){}

    public void sendRequest(Byte[] bytes){}

    public AbstractResponse receiveResponse(){
        return new GetDataResponse();
    }
}
