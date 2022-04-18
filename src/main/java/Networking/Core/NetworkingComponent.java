package Networking.Core;

import Networking.Messages.*;
import Networking.Requests.AbstractRequest;
import Networking.Swarm.Swarm;
import Networking.Swarm.SwarmManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NetworkingComponent {

    private SwarmManager swarmManager;
    private IncomingTrafficHandler trafficHandler;
    private int port;

    Socket initialConnection;

    public NetworkingComponent(int port){
        try {
            this.port = port;
            swarmManager = new SwarmManager(port);
            trafficHandler = new IncomingTrafficHandler(this, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void joinSwarm(Integer swarmID){
        if(swarmManager.getByID(swarmID) == null){
            swarmManager.addSwarm(swarmID);
        }
    }

    public void start(){
        new Thread(trafficHandler).start();
    }

    public void addPeer(Integer swarmID, String ip, Socket peerSocket, Integer userID) throws IOException {
        swarmManager.getByID(swarmID).addPeer(ip,peerSocket , userID);
    }

    public void connect(String ip) throws IOException {
        this.setInitialConnection(new Socket(ip,this.port));
    }

    public void setInitialConnection(Socket initialConnection) {
        this.initialConnection = initialConnection;
    }

    public SwarmManager getSwarmManager() {
        return swarmManager;
    }

    public void handleMessage(ParseableMessage msg, Socket source) throws IOException {
        if(source == null){
            sendMessage(msg);
        } else if(msg.getSwarmID() == Messages.NO_SWARM){
            System.out.println("header: " + msg.getHeader());
            switch (msg.getHeader()) {
                case MessageHeader.NEW_CONNECTION_REQUEST: {
                    ConnectMessage received = new ConnectMessage(msg.getRawMessage());
                    System.out.println(Arrays.toString(msg.getRawMessage()));
                    int newID;
                    do {
                        newID = (int) (Math.random() * Integer.MAX_VALUE);
                    } while (swarmManager.getByID(received.getDestination()).getPeers().containsKey(newID));
                    ConnectAcceptMessage response = new ConnectAcceptMessage(received.getDestination(), newID, swarmManager.getByID(received.getDestination()).getSelfID());
                    swarmManager.getByID(received.getDestination()).addPeer(source.getInetAddress().toString(), source, newID);
                    swarmManager.getByID(received.getDestination()).getPeers().get(newID).getPeerSocket().getOutputStream().write(response.toPacket());
                    break;
                }
                case MessageHeader.NEW_CONNECTION_RESPONSE: {
                    System.out.println("should have 28 bytes " + Arrays.toString(msg.getRawMessage()));
                    ConnectAcceptMessage received = new ConnectAcceptMessage(msg.getRawMessage());
                    swarmManager.addSwarm(received.getDestination());
                    swarmManager.getByID(received.getDestination()).setSelfID(received.getNewUserID());
                    swarmManager.getByID(received.getDestination()).addPeer(source.getInetAddress().toString(), source, received.getSenderID());

                    RequestPeersMessage response = new RequestPeersMessage(received.getDestination(), swarmManager.getByID(received.getDestination()).getSelfID());
                    break;
                }
            }
        } else if(swarmManager.getByID(msg.getSwarmID()) != null){
                swarmManager.getByID(msg.getSwarmID()).handleMessage(msg,source);
        }
    }

    public void sendMessage(SendableMessage msg) throws IOException {
        initialConnection.getOutputStream().write(msg.toPacket());
    }
}
