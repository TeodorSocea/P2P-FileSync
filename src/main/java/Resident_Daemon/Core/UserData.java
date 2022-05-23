package Resident_Daemon.Core;

import Networking.Swarm.NetworkSwarm;
import Networking.Utils.Invitation;
import Version_Control.FileP2P;

import java.io.File;
import java.util.ArrayList;
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
    private boolean isEnableToWriteAllFiles;
    private int lastCreatedSwarm;


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

    public int getLastCreatedSwarm() {
        return lastCreatedSwarm;
    }

    public void setLastCreatedSwarm(int lastCreatedSwarm) {
        this.lastCreatedSwarm = lastCreatedSwarm;
    }
//endregion
}
