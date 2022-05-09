package Networking.Networking;

import Networking.Messages.InviteMessage;
import Networking.Messages.MessageHeader;
import Networking.Networking.ConnectionHandler;
import Networking.Networking.NetworkListener;
import Networking.Swarm.NetworkSwarm;
import Networking.Swarm.NetworkSwarmManager;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkManager{
    private int port;
    private List<Socket> commonSocketPool;
    private Map<Socket, ConnectionHandler> connectionHandlers;
    private NetworkSwarmManager networkSwarmManager;
    private NetworkListener networkListener;

    public NetworkManager(int port, NetworkSwarmManager networkSwarmManager) throws IOException {
        this.port = port;
        this.networkSwarmManager = networkSwarmManager;
        this.commonSocketPool = new ArrayList<Socket>();
        this.connectionHandlers = new HashMap<>();
        this.networkListener = new NetworkListener(this.port, this.commonSocketPool, networkSwarmManager, connectionHandlers);
        new Thread(networkListener).start();
    }

    public void connectToIP(String ip) throws IOException {
        Socket newSocket = new Socket(ip, port);
        commonSocketPool.add(newSocket);
    }

    public void inviteIPToSwarm(String ip, NetworkSwarm swarm) throws IOException {
        Socket newSocket = new Socket(ip, port);
        commonSocketPool.add(newSocket);

        InviteMessage invitation = new InviteMessage(MessageHeader.INVITE_TO_SWARM, swarm.getSelfID(), swarm.generateNextID(), swarm.getSwarmID());
        newSocket.getOutputStream().write(invitation.toPacket());

        ConnectionHandler connectionHandler = new ConnectionHandler(newSocket, networkSwarmManager, commonSocketPool);
        connectionHandlers.put(newSocket, connectionHandler);

        new Thread(connectionHandler).start();
    }

    public List<Socket> getCommonSocketPool(){
        return commonSocketPool;
    }

}
