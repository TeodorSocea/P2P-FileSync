package Networking.Utils;

import java.util.*;

public class DataPipeline {
    private Map<Integer, List<Data>> pipeline;
    private Map<Integer, Integer> latestIndex;

    public DataPipeline() {
        this.pipeline = new HashMap<>();
        this.latestIndex = new HashMap<>();
    }

    public List<Data> getDataInPipeline(int peerId){
        return pipeline.get(peerId);
    }

    public void addData(Integer peerId, byte[] data, int latestIndex){
        if(pipeline.containsKey(peerId)){
            if(pipeline.get(peerId).size() != latestIndex+1){
                pipeline.get(peerId).add(new Data());
            }
            pipeline.get(peerId).get(latestIndex).getData().add(data);
        }else{
            pipeline.put(peerId, new ArrayList<>());
            pipeline.get(peerId).add(new Data());
            pipeline.get(peerId).get(latestIndex).getData().add(data);
        }
    }

    public int getLatestIndexOfPeer(int peerID){
        if(latestIndex.containsKey(peerID)){
            return latestIndex.get(peerID);
        }
        latestIndex.put(peerID, 0);
        return latestIndex.get(peerID);
    }

    public void updateLatestIndexOfPeer(int peerID){
        int index = latestIndex.get(peerID);
        latestIndex.put(peerID, index+1);
    }
}
