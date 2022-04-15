package Networking.Tests;

import Networking.Swarm.Swarm;
import Networking.Swarm.SwarmManager;

import java.io.IOException;
import java.net.Socket;

public class TestProperP2P {
    public static void main(String[] args) throws IOException {

        if(args[0].equals("1")){ //create new swarm and wait for new connections
            //swarmManager.addSwarm(0,new Swarm(33000));
            System.out.println("making new swarm");
            //Swarm swarm = new Swarm(33000);

        }else if(args[0].equals("0")){ //join existing swarm
            System.out.println("joining existing swarm");
            Socket s = new Socket("192.168.0.100",33000);
            byte[] test = new byte[4];
            test[0] = 1;
            test[1] = 2;
            test[2] = 3;
            test[3] = 4;
            s.getOutputStream().write(test);
        } else {
            System.out.println("no bueno");
        }
    }
}
