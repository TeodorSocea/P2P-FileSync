package Version_Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class FileP2P {
    String fileName;
    String data = "";
    Timestamp timestamp;

    public FileP2P(String filename) {
        this.fileName = filename;
        readFile(filename);
        this.timestamp = time();
    }

    public FileP2P(String fileName, String data) {
        this.fileName = fileName;
        this.data = data;
        this.timestamp = time();
    }

    private Timestamp time() {
        Date date = new Date();
        Timestamp timest = new Timestamp(date.getTime());
        return timest;
    }

    public boolean binaryCheck() {
        char ch;
        for(int i = 0; i < this.data.length(); i++) {
            ch = this.data.charAt(i);
            if(ch != '0' && ch != '1')
                return false;
        }
        return true;
    }

    private void readFile(String filename) {
        try (FileInputStream file = new FileInputStream(new File(filename))) {
            int content;
            while((content = file.read()) != -1) {
                this.data += (char)content;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getData() {
        return this.data;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
