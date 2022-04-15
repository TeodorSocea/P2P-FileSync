package Networking.Core;

import Networking.Messages.*;
import Networking.Requests.AbstractRequest;
import Networking.Swarm.Swarm;
import Networking.Swarm.SwarmManager;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;


public class NetworkingComponent {
    private SwarmManager swarmManager;
    private IncomingTrafficHandler trafficHandler;
    int port;

    Socket initialConnection;

    public NetworkingComponent(int port){
        try {
            this.port = port;
            swarmManager = new SwarmManager();
            trafficHandler = new IncomingTrafficHandler(this, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void joinSwarm(Integer swarmID){
        if(swarmManager.getByID(swarmID) == null){
            swarmManager.addSwarm(swarmID,new Swarm(swarmID));
        }
    }

    public void start(){
        new Thread(trafficHandler).start();
    }


/*
    public List<Swarm> getSwarms(){
        //System.out.println(swarmManager.get(0).getPeers());
        return swarmManager;
    }
*/
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
            if(msg.getHeader() == MessageHeader.NEW_CONNECTION_REQUEST){

                ConnectMessage tmp = new ConnectMessage(msg.getRawMessage());
                System.out.println(Arrays.toString(msg.getRawMessage()));
                int tmpID;
                do{
                    tmpID = (int)(Math.random() * Integer.MAX_VALUE);
                }while(swarmManager.getByID(tmp.getDestination()).getPeers().containsKey(tmpID));

                swarmManager.getByID(tmp.getDestination()).addPeer(source.getInetAddress().toString(),source,tmpID);
            }
        } else {
            if(swarmManager.getByID(msg.getSwarmID()) != null){
                swarmManager.getByID(msg.getSwarmID()).handleMessage(msg);
            }
        }

        System.out.println(Arrays.toString(msg.getRawMessage()));
    }

    public void sendMessage(SendableMessage msg) throws IOException {
        initialConnection.getOutputStream().write(msg.toPacket());
    }
}
