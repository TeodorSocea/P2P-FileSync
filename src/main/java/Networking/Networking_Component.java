package Networking;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Networking_Component {
    private List<Swarm> Swarms;

    public Networking_Component(int port){
        Swarms = new ArrayList<Swarm>();
        Swarms.add(new Swarm(port));
    }

    public List<Swarm> getSwarms(){
        System.out.println(Swarms.get(0).getPeers());
        return Swarms;
    }

    public void connect(String ip, int port) throws IOException {
        Swarms.get(0).addPeer(ip, port);
    }

}
