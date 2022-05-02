package Networking.Core;

import java.net.Socket;

public class Invitation {
    private int senderID;
    private int swarmID;
    private int selfID;
    private Socket socket;

    public Invitation(int senderID, int swarmID, int selfID, Socket socket) {
        this.senderID = senderID;
        this.swarmID = swarmID;
        this.selfID = selfID;
        this.socket = socket;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getSwarmID() {
        return swarmID;
    }

    public int getSelfID() {
        return selfID;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "senderID=" + senderID +
                ", swarmID=" + swarmID +
                ", selfID=" + selfID +
                ", socket=" + socket +
                '}';
    }
}
