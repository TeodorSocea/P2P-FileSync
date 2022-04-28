package Networking.Core;

import Networking.Messages.Messages;
import Networking.Messages.ParseableMessage;
import Networking.Swarm.Swarm;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Map;

public class SocketHandler implements Runnable{

    private NetworkingComponent parent;
    private Socket mySocket;

    private Map<Integer, Swarm> relevantSwarms;

    public SocketHandler(NetworkingComponent parent, Socket mySocket) {
        this.parent = parent;
        this.mySocket = mySocket;
    }

    @Override
    public void run() {

        try {
            while(true){
                byte[] buf = new byte[Messages.MAX_SIZE];
                InputStream in = mySocket.getInputStream();
                System.out.println("Blocked");
                int nr = in.read(buf);
                System.out.println("Unblocked");
                System.out.println(nr);
                if(nr!=0) {
                    byte[] toSend = Arrays.copyOfRange(buf, 0, nr);
                    System.out.println("Parent will handle message");
                    parent.handleMessage(new ParseableMessage(toSend),mySocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
