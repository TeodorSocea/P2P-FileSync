package Resident_Daemon.Core;

import Resident_Daemon.Utils.BasicFileUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class SyncRecord implements Serializable, Comparable<SyncRecord> {
    private String fileRelPath;
    private boolean isSynced;
    private long lastModifiedTimeStamp;

    public SyncRecord(String fileRelPath, boolean isSynced, long lastModifiedTimeStamp) {
        this.fileRelPath = fileRelPath;
        this.isSynced = isSynced;
        this.lastModifiedTimeStamp = lastModifiedTimeStamp;
    }

    public void setFileRelPath(String fileRelPath) {
        this.fileRelPath = fileRelPath;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public void setLastModifiedTimeStamp(long lastModifiedTimeStamp) {
        this.lastModifiedTimeStamp = lastModifiedTimeStamp;
    }

    public boolean getSynced() {
        return this.isSynced;
    }

    public String getFileRelPath() {
        return fileRelPath;
    }

    public long getLastModifiedTimeStamp() {
        return lastModifiedTimeStamp;
    }

    @Override
    public String toString() {
        return "SyncRecord{" +
                "filePath=" + fileRelPath +
                ", isSynced=" + isSynced +
                ", getLastModifiedTimeStamp=" + lastModifiedTimeStamp +
                '}';
    }

    @Override
    public int compareTo(SyncRecord rhs) {
        if (this.getFileRelPath().equals(rhs.getFileRelPath()))
            return 0;

        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncRecord that = (SyncRecord) o;
        return fileRelPath.equals(that.fileRelPath);
    }

}
