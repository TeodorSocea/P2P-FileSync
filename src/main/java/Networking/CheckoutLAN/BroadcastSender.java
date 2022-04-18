package Networking.CheckoutLAN;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BroadcastSender implements Runnable {
    private DatagramSocket socket;
    private int port;
    private String broadcastMsg = "check";
    private static final int SIZE_BYTES = 100;

    public BroadcastSender(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        while (true) {

            //broadcast mesage over LAN at predefined port
            try {
                socket = new DatagramSocket();
                socket.setBroadcast(true);
                byte[] buffer = broadcastMsg.getBytes();

                DatagramPacket packet = null;

                packet = new DatagramPacket(buffer, buffer.length,
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

            //send package every 30 seconds
            //in the future use scheduledExecutor probably better
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
