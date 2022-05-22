package Version_Control;

import java.io.FileInputStream;
import java.io.IOException;

public class FileP2P {
    String fileName;
    String data = "";
    long timestamp;


    public FileP2P(FileInputStream file, String fileName, long timestamp) throws IOException {
        int content;
        while((content = file.read()) != -1) {
            this.data += (char)content;
        }
        this.fileName = fileName;

        this.timestamp = timestamp;
    }
    public FileP2P(FileP2P file){
        this.fileName = file.getFileName();
        this.data=file.getData();
        this.timestamp= file.getTimestamp();
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

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}