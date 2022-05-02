package Networking.Core;


import java.util.HashMap;
import java.util.Map;

public class DataBuffer {
    private Map<Integer, byte[]> dataMap;

    public DataBuffer() {
        this.dataMap = new HashMap<>();
    }

    public void addToData(int chunkID, byte[] data){
        this.dataMap.put(chunkID, data);
    }

    public Map<Integer, byte[]> getDataMap() {
        return dataMap;
    }
}
