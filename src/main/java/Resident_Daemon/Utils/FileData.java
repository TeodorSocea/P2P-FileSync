package Resident_Daemon.Utils;

public class FileData {
    private String fileRelPath;
    private long timeStamp;
    private String fileContent;

    public FileData(String fileRelPath, long timeStamp, String fileContent) {
        this.fileRelPath = fileRelPath;
        this.timeStamp = timeStamp;
        this.fileContent = fileContent;
    }

    public String getFileRelPath() {
        return fileRelPath;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getFileContent() {
        return fileContent;
    }
}
