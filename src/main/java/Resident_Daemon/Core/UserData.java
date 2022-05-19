package Resident_Daemon.Core;

import Networking.Swarm.NetworkSwarm;
import Networking.Utils.Invitation;

import java.util.List;
import java.util.Map;

public class UserData {
    private List<Invitation> userInvitations;
    private List<String> nearbyIPs;
    private Map<Integer, NetworkSwarm> mySwarms;


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

    //endregion
}
