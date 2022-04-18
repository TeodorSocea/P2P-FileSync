package Networking.Core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class IncomingTrafficHandler implements Runnable{
    private List<Socket> peerSockets;
    private List<Thread> peerSocketListeners; //maybe
    private ServerSocket listener;
    private NetworkingComponent parent;

    public IncomingTrafficHandler(NetworkingComponent parent, int port) throws IOException {
        this.parent = parent;
        peerSockets = new ArrayList<>();
        listener = new ServerSocket(port);
        listener.setReuseAddress(true);
    }

    @Override
    public void run() {
        Socket newSocket = null;
        while(true){
            try {
                newSocket = listener.accept();

                //for now just send all the incoming connections to one swarm
                if(newSocket != null) {
                    System.out.println("New connection from " + newSocket.getRemoteSocketAddress().toString());
                    SocketHandler sh=new SocketHandler(parent,newSocket);//Richi
                    peerSockets.add(newSocket);
                    new Thread(sh).start();
                }
            } catch (IOException e){
                    e.printStackTrace();
            }
        }
    }
}
