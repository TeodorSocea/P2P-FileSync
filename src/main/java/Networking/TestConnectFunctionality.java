package Networking;

import java.io.IOException;

public class TestConnectFunctionality {
    public static void main(String args[]) throws IOException {
        Networking_Component nc = new Networking_Component();
        nc.connect("127.0.0.1");
        System.out.println("Yes");
    }
}
