package Networking.Networking;

import Networking.Swarm.NetworkSwarmManager;
import Networking.Utils.DataPipeline;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class NetworkListener implements Runnable{

    private int port;
    private ServerSocket listener;
    private List<Socket> commonSocketPool;
    private NetworkSwarmManager networkSwarmManager;
    private Map<Socket, ConnectionHandler> connectionHandlers;
    private Map<Integer, DataPipeline> dataPipelineMap;

    public NetworkListener(int port, List<Socket> commonSocketPool, NetworkSwarmManager networkSwarmManager, Map<Socket, ConnectionHandler> connectionHandlers, Map<Integer, DataPipeline> dataPipelineMap) throws IOException {
        this.port = port;
        this.commonSocketPool = commonSocketPool;
        this.networkSwarmManager = networkSwarmManager;
        this.connectionHandlers = connectionHandlers;
        listener = new ServerSocket(port);
        this.dataPipelineMap = dataPipelineMap;
        listener.setReuseAddress(true);
    }

    @Override
    public void run() {
        while(true){
            try {
                Socket newSocket = listener.accept();
                commonSocketPool.add(newSocket);
                ConnectionHandler connectionHandler = new ConnectionHandler(newSocket, networkSwarmManager, commonSocketPool, dataPipelineMap);
                new Thread(connectionHandler).start();
                connectionHandlers.put(newSocket, connectionHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
