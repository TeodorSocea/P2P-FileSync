package Version_Control;

import java.io.FileInputStream;
import java.io.IOException;

public class FileP2P {
    String fileName;
    String data = "";
    long timestamp;

    public FileP2P(){}
    public FileP2P(FileInputStream file, String fileName, long timestamp) throws IOException {
        int content;
        while((content = file.read()) != -1) {
            this.data += (char)content;
        }
        this.fileName = fileName;

        this.timestamp = timestamp;
    }


    public String getFileName() {
        return this.fileName;
    }

    public String getData() {
        return this.data;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}