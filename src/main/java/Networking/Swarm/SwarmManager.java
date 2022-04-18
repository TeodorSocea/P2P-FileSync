package Networking.Swarm;

import java.util.HashMap;
import java.util.Map;

public class SwarmManager {
    private Map<Integer,Swarm> swarms;
    private int port;
    public SwarmManager(int port){
        swarms = new HashMap<>();
        this.port = port;
    }

    public void addSwarm(Integer swarmID){
        swarms.put(swarmID, new Swarm(swarmID, port));
    }

    public Swarm getByID(Integer id){
        return swarms.get(id);
    }
}
