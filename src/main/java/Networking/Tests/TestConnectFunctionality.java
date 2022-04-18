package Networking.Tests;

import java.io.IOException;
import java.net.Socket;

import Networking.Core.NetworkingComponent;
import Networking.Messages.ConnectMessage;

public class TestConnectFunctionality {
    public static void main(String args[]) throws IOException {

        NetworkingComponent nc = new NetworkingComponent(32001);
        nc.start();

        if(args[0].equals("0")){ //swarm initial connection
            nc.joinSwarm(44);
            nc.getSwarmManager().getByID(44).setSelfID(69);
        } else if(args[0].equals("1")){ //connecting peer
            nc.connect(args[1]);
            nc.handleMessage(new ConnectMessage(44),null);
        }else {
            System.out.println("wrong args");
        }
        //nc.connect(0,"127.0.0.1", 32000,4);
    }

}
