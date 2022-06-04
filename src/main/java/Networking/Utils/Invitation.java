package Networking.Utils;

import Networking.Messages.Message;

import java.io.IOException;
import java.net.Socket;

public class Invitation {
    private int senderID;
    private int swarmID;
    private String swarmName;
    private int selfID;
    private Socket socket;

    public Invitation(int senderID, int swarmID, String swarmName, int selfID, Socket socket) {
        this.senderID = senderID;
        this.swarmID = swarmID;
        this.swarmName = swarmName;
        this.selfID = selfID;
        this.socket = socket;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getSwarmID() {
        return swarmID;
    }

    public String getSwarmName() {
        return swarmName;
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
                ", swarmName=" + swarmName +
                ", selfID=" + selfID +
                ", socket=" + socket +
                '}';
    }
}
