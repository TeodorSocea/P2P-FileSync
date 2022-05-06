package Networking.Messages;

import Networking.Peer.Peer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class DataPipeline {
    private Map<Peer, Queue<Data>> dataMap;

    public DataPipeline() {
        this.dataMap = new HashMap<>();
    }


    public Queue<Data> getByPeerId(Integer peerId){
        for (Map.Entry<Peer,Queue<Data>> entry : dataMap.entrySet()){
              if(entry.getKey().getPeerID() == peerId){
                  return entry.getValue();
              }
        }
        return null; /* I don't know if it is ok to return null in case I don't find the given peerId in the map */
    }

    public void addData(Integer peerId, Data data){
           /* I didn't understand what you've asked me to do here */
    }
}
