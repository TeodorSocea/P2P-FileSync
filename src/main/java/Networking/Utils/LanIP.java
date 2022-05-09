package Networking.Utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class LanIP {

    private static final String IPV4_PATTERN ="^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    public static String getLanIP() throws SocketException {
        Enumeration<NetworkInterface> iterNetwork = NetworkInterface.getNetworkInterfaces();
        while (iterNetwork.hasMoreElements()){
            NetworkInterface net=iterNetwork.nextElement();
            if (!net.isUp())
                continue;

            if (net.isLoopback())
                continue;

                Enumeration<InetAddress> iterAddr = net.getInetAddresses();
                while (iterAddr.hasMoreElements()){
                    InetAddress addr=iterAddr.nextElement();
                    if (addr.isAnyLocalAddress())
                        continue;

                    if (addr.isLoopbackAddress())
                        continue;

                    if (addr.isMulticastAddress())
                        continue;


                        if (addr.getHostAddress().matches(IPV4_PATTERN))
                            return addr.getHostAddress();
                    }
                }
        return null;
    }



}
