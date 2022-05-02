package Networking.Networking;

import Networking.Networking.ConnectionHandler;
import Networking.Swarm.NetworkSwarmManager;

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

    public NetworkListener(int port, List<Socket> commonSocketPool, NetworkSwarmManager networkSwarmManager, Map<Socket, ConnectionHandler> connectionHandlers) throws IOException {
        this.port = port;
        this.commonSocketPool = commonSocketPool;
        this.networkSwarmManager = networkSwarmManager;
        this.connectionHandlers = connectionHandlers;
        listener = new ServerSocket(port);
        listener.setReuseAddress(true);
    }

    @Override
    public void run() {
        while(true){
            try {
                Socket newSocket = listener.accept();
                commonSocketPool.add(newSocket);
                ConnectionHandler connectionHandler = new ConnectionHandler(newSocket, networkSwarmManager, commonSocketPool);
                new Thread(connectionHandler).start();
                connectionHandlers.put(newSocket, connectionHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
