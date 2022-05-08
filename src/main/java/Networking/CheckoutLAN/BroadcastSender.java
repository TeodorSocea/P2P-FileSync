package Networking.CheckoutLAN;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BroadcastSender{
    private int port,delay;
    private String broadcastMsg = "check";
    private static final int SIZE_BYTES = 100;
    /**
     * This method is used for initialization.
     * @param port the port on which the socket will send packets
     * @param delay the amount of seconds between broadcasting
     */
    public BroadcastSender(int port, int delay) {
        this.port = port;
        this.delay = delay;
    }
    /**
     * This method creates a socket and broadcast on the LAN a packet for all BroadcastReceiver
     */
    private Runnable taskBroadcast=new Runnable(){
        @Override
        public void run() {
            try {

                //sends a broadcast on predefined port LAN
                DatagramSocket socket = new DatagramSocket();
                socket.setBroadcast(true);
                byte[] buffer = broadcastMsg.getBytes();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                        InetAddress.getByName("255.255.255.255"), port);
                socket.send(packet);
                socket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * This method uses a scheduler to start broadcasts with a delay
     */
    public void startBroadcast(){
        ScheduledExecutorService executorService= Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(taskBroadcast,0,delay,TimeUnit.SECONDS);
    }



}