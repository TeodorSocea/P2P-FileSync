package Networking.CheckoutLAN;

import java.io.IOException;
import java.net.*;

public class BroadcastReceiver implements Runnable {
    private DatagramSocket socket;
    private int port;
    private static final int SIZE_BYTES = 10;

    public BroadcastReceiver( int port) {
        this.port = port;
    }

    public void startListening() throws SocketException {
        try {
            this.port = port;
            socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true) {
            //receives packets from any address
            try {
                socket.setBroadcast(true);
                byte[] recvBuf = new byte[SIZE_BYTES];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                System.out.println("Packet from: " + packet.getAddress().getHostAddress());



            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
