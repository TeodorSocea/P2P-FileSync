package Version_Control;

import java.util.Objects;

public class FileP2P {
    String fileName;
    String userName;
    String data;
    int hash;

    public FileP2P(String fileName, String userName, String data) {
        this.fileName = fileName;
        this.userName = userName;
        this.data = data;
        this.hash = hashCode();
    }

    public FileP2P(String fileName, String userName, String data, int hash) {
        this.fileName = fileName;
        this.userName = userName;
        this.data = data;
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileP2P fileP2P = (FileP2P) o;
        return hash == fileP2P.hash && Objects.equals(fileName, fileP2P.fileName) && Objects.equals(userName, fileP2P.userName) && Objects.equals(data, fileP2P.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, userName, data, hash);
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getData() {
        return this.data;
    }

    public int getHash() {
        return this.hash;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }
}
