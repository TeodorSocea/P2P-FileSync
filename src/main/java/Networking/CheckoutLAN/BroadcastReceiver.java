package Networking.CheckoutLAN;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class BroadcastReceiver implements Runnable {
    private DatagramSocket socket;
    private int port;
    private static final int SIZE_BYTES = 10;
    private Set<String> ipSet;
    private List<String> definitiveIpSet;
    private String selfIP;

    private Runnable clearIpSet=new Runnable() {
        @Override
        public void run() {
            if(!definitiveIpSet.equals(ipSet.toArray())){
                definitiveIpSet = new ArrayList<>(ipSet);
            }
            ipSet.clear();
        }
    };
    /**
     *This method is used for initialization.
     * @param port the port on which the socket will listen
     * @param delay the amount of seconds between refreshing of the list of ips from which a packet has been received
     */
    public BroadcastReceiver( int port,int delay) {
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            selfIP = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
        ipSet= new HashSet<>();
        definitiveIpSet = new ArrayList<String>();
        ScheduledExecutorService executorService= Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(clearIpSet,0,delay, TimeUnit.SECONDS);
    }

    /**
     * This method will create a socket for listening and start a thread for the packets to be handled.
     * @throws SocketException
     */
    public void startListening() throws SocketException {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
            new Thread(this).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public List<String> getIpSet() {
        return definitiveIpSet;
    }

    /**
     * This method receives packets and adds the sender ip to the set.
     */
    @Override
    public void run() {

        while (true) {
            try {
                socket.setBroadcast(true);
                byte[] recvBuf = new byte[SIZE_BYTES];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                if(!packet.getAddress().getHostAddress().toString().equals(selfIP))
                    ipSet.add(packet.getAddress().getHostAddress().toString());
                //System.out.println(ipSet);

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
