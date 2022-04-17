package Networking.Core;

import Networking.Messages.Messages;
import Networking.Messages.ParseableMessage;
import Networking.Peer.Peer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
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
        listener.setSoTimeout(500);
    }

    @Override
    public void run() {
        Socket newSocket = null;
        while(true){
            try {
                newSocket = listener.accept();

                //figure out a way to multiplex all inputs from peerSockets and listener (I low-key miss C in this aspect)
                //for now just go through all the sockets really fast and check if a message was sent

                //for now just send all the incoming connections to one swarm
                if(newSocket != null) {
                    /*
                    byte[] confirmMessage = new byte[16];
                    OutputStream dOut = newSocket.getOutputStream();
                    dOut.write(confirmMessage, 0, 16);
                    newSocket.setSoTimeout(50); //50 ms
                    peerSockets.add(newSocket);
                    */
                    System.out.println("New connection from " + newSocket.getRemoteSocketAddress().toString());
                    //newSocket.setSoTimeout(50);
                    SocketHandler sh=new SocketHandler(parent,newSocket);//Richi
                    new Thread(sh).start();


                    peerSockets.add(newSocket);

                    //parent.addPeer(44, newSocket.getInetAddress().toString(), newSocket,(int)(Math.random()* 100000));

                    //System.out.println(parent.getSwarmManager().getByID(44));

                    newSocket = null;
                }
            } catch (SocketTimeoutException ignored){
                //System.out.println("it is intentional");
            } catch (IOException e){
                    e.printStackTrace();
            }

            /*for (Socket s : peerSockets) {
                try {
                    byte[] buf = new byte[Messages.MAX_SIZE];
                    InputStream in = s.getInputStream();
                    int nr = in.read(buf, 0, in.available());
                    System.out.println(nr);
                    if(nr!=0) {
                        byte[] toSend = Arrays.copyOfRange(buf, 0, nr);
                        parent.handleMessage(new ParseableMessage(toSend),s);
                    }
                } catch (SocketTimeoutException ignored) {
                    System.out.println("it is intentional");
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }*/
        }
    }
}
