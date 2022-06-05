package Resident_Daemon.Core;

import Networking.Core.NetworkingComponent;
import Networking.Swarm.NetworkSwarm;
import Networking.Utils.Invitation;
import Version_Control.FileP2P;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData {
    private List<Invitation> userInvitations;
    private List<String> nearbyIPs;
    private Map<Integer, NetworkSwarm> mySwarms;
    private boolean isConnected = false;
    private String selfIP;
    private List<FileP2P> localFiles;
    private List<FileP2P> otherFiles;

    private boolean isCompareDone = false;

    private boolean isEnableToWriteAllFiles;
    private NetworkSwarm lastCreatedSwarm;

    private List<SyncRecord> localMasterFile;

    private List<SyncRecord> otherMasterFile;

    private boolean isReceivedMasterFile;

    private ArrayList<String> versionFileFiles;
    private ArrayList<String> timestampVersionFileFiles;
    private HashMap<String, Map<String,String>> changesFileVersionFile;

    private Integer receivedFiles = 0;
    private Integer requiredFiles = 0;


    //region Getters & Setters

    public List<Invitation> getUserInvitations() {
        return userInvitations;
    }

    public void setUserInvitations(List<Invitation> userInvitations) {
        this.userInvitations = userInvitations;
    }

    public List<String> getNearbyIPs() {
        return nearbyIPs;
    }

    public void setNearbyIPs(List<String> nearbyIPs) {
        this.nearbyIPs = nearbyIPs;
    }

    public Map<Integer, NetworkSwarm> getMySwarms() {
        return mySwarms;
    }

    public void setMySwarms(Map<Integer, NetworkSwarm> mySwarms) {
        this.mySwarms = mySwarms;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected, Map<Integer, NetworkSwarm> mySwarms) {
        isConnected = connected;
        this.mySwarms = mySwarms;
    }

    public String getSelfIP() {
        return selfIP;
    }

    public void setSelfIP(String selfIP) {
        this.selfIP = selfIP;
    }

    public void resetFileLists(){
        localFiles = new ArrayList<>();
        otherFiles = new ArrayList<>();
    }

    public void addToOtherFilesList(FileP2P file){
        otherFiles.add(file);
    }

    public List<FileP2P> getLocalFiles() {
        return localFiles;
    }

    public List<FileP2P> getOtherFiles() {
        return otherFiles;
    }

    public boolean isEnableToWriteAllFiles() {
        return isEnableToWriteAllFiles;
    }

    public void setEnableToWriteAllFiles(boolean enableToWriteAllFiles) {
        isEnableToWriteAllFiles = enableToWriteAllFiles;
    }

    public NetworkSwarm getLastCreatedSwarm() {
        return lastCreatedSwarm;
    }

    public void setLastCreatedSwarm(NetworkSwarm lastCreatedSwarm) {
        this.lastCreatedSwarm = lastCreatedSwarm;
    }

    public void resetMasterFiles(){
        localMasterFile = new ArrayList<>();
        otherMasterFile = new ArrayList<>();
    }

    public boolean isReceivedMasterFile() {
        return isReceivedMasterFile;
    }

    public void setReceivedMasterFile(boolean receivedMasterFile) {
        isReceivedMasterFile = receivedMasterFile;
    }

    public List<SyncRecord> getLocalMasterFile() {
        return localMasterFile;
    }

    public List<SyncRecord> getOtherMasterFile() {
        return otherMasterFile;
    }

    public ArrayList<String> getVersionFileFiles() {
        return versionFileFiles;
    }

    public void setVersionFileFiles(ArrayList<String> versionFileFiles) {
        this.versionFileFiles = versionFileFiles;
    }

    public ArrayList<String> getTimestampVersionFileFiles() {
        return timestampVersionFileFiles;
    }

    public void setTimestampVersionFileFiles(ArrayList<String> timestampVersionFileFiles) {
        this.timestampVersionFileFiles = timestampVersionFileFiles;
    }

    public HashMap<String, Map<String, String>> getChangesFileVersionFile() {
        return changesFileVersionFile;
    }

    public void setChangesFileVersionFile(HashMap<String, Map<String, String>> changesFileVersionFile) {
        this.changesFileVersionFile = changesFileVersionFile;
    }

    public boolean isCompareDone() {
        return isCompareDone;
    }

    public void setCompareDone(boolean compareDone) {
        isCompareDone = compareDone;
    }

    public Integer getReceivedFiles() {
        return receivedFiles;
    }

    public void setReceivedFiles(Integer receivedFiles) {
        this.receivedFiles = receivedFiles;
    }

    public Integer getRequiredFiles() {
        return requiredFiles;
    }

    public void setRequiredFiles(Integer requiredFiles) {
        this.requiredFiles = requiredFiles;
    }

    //endregion

}
