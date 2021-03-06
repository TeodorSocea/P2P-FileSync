package Networking.Networking;

import Networking.Messages.InviteMessage;
import Networking.Messages.MessageHeader;
import Networking.Swarm.NetworkSwarm;
import Networking.Swarm.NetworkSwarmManager;
import Networking.Utils.DataPipeline;

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
    private Map<Integer, DataPipeline> dataPipelineMap;

    public NetworkManager(int port, NetworkSwarmManager networkSwarmManager, Map<Integer, DataPipeline> dataPipelineMap) throws IOException {
        this.port = port;
        this.networkSwarmManager = networkSwarmManager;
        this.dataPipelineMap = dataPipelineMap;
        this.commonSocketPool = new ArrayList<Socket>();
        this.connectionHandlers = new HashMap<>();
        this.networkListener = new NetworkListener(this.port, this.commonSocketPool, networkSwarmManager, connectionHandlers, this.dataPipelineMap);
        new Thread(networkListener).start();
    }

    public void connectToIP(String ip) throws IOException {
        Socket newSocket = new Socket(ip, port);
        commonSocketPool.add(newSocket);
    }

    public void inviteIPToSwarm(String ip, NetworkSwarm swarm) throws IOException {
        Socket newSocket = new Socket(ip, port);
        commonSocketPool.add(newSocket);

        InviteMessage invitation = new InviteMessage(MessageHeader.INVITE_TO_SWARM, swarm.getSelfID(), swarm.generateNextID(), swarm.getSwarmID(), swarm.getSwarmName());
        newSocket.getOutputStream().write(invitation.toPacket());

        ConnectionHandler connectionHandler = new ConnectionHandler(newSocket, networkSwarmManager, commonSocketPool, dataPipelineMap);
        connectionHandlers.put(newSocket, connectionHandler);

        new Thread(connectionHandler).start();
    }

    public List<Socket> getCommonSocketPool(){
        return commonSocketPool;
    }

}
