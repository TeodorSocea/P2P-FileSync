package Networking.Utils;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<byte[]> data;

    public Data() {
       this.data = new ArrayList<>();
    }

    public List<byte[]> getData(){
        return data;
    }
}
