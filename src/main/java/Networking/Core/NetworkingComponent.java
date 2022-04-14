package Networking.Core;

import Networking.Swarm.Swarm;
import Networking.Swarm.SwarmManager;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Networking_Component {
    private SwarmManager swarmManager;

    public Networking_Component(int port){
        swarmManager = new SwarmManager();
        swarmManager.addSwarm(0,new Swarm(port));
    }

/*
    public List<Swarm> getSwarms(){
        //System.out.println(swarmManager.get(0).getPeers());
        return swarmManager;
    }
*/

    public void connect(Integer swarmID, String ip, int port, Integer userID) throws IOException {
        swarmManager.getByID(swarmID).addPeer(ip, port, userID);
    }

}
