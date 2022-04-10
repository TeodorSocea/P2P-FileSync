package Networking;

import java.io.IOException;

public class TestConnectFunctionality {
    public static void main(String args[]) throws IOException {
        Networking_Component nc = new Networking_Component(32001);
        nc.connect("127.0.0.1", 32000);
        nc.getSwarms().get(0).closePeer();
    }

}
