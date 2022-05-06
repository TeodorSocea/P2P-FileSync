package Networking.Messages;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<byte[]> data;

    public Data() {
        this.data = new ArrayList<>();
    }
    public Data (byte[] data){
        this.data.add(data);
    }
}
