package Networking.Tests;

import Networking.Core.NetworkingComponent;
import Networking.Messages.Messages;

import java.nio.ByteBuffer;

public class TestTimeout {
    public static void main(String[] args){
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(1024);
        System.out.println(Messages.getIntFromByteArray(b.array(),0));

        //NetworkingComponent network = new NetworkingComponent(33000);
        //network.start();
    }
}
