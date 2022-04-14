package Networking;

import java.io.IOException;
import Networking.Swarm.*;

public class TestConnectFunctionality {
    public static void main(String args[]) throws IOException {
        Networking_Component nc = new Networking_Component(32001);
        nc.connect(0,"127.0.0.1", 32000,4);
    }

}
