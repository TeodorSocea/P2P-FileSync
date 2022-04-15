package Networking.Swarm;

import java.util.HashMap;
import java.util.Map;

public class SwarmManager {
    private Map<Integer,Swarm> swarms;

    public SwarmManager(){
        swarms = new HashMap<>();
    }

    public void addSwarm(Integer swarmID, Swarm newSwarm){
        swarms.put(swarmID,newSwarm);
    }

    public Swarm getByID(Integer id){
        return swarms.get(id);
    }
}
