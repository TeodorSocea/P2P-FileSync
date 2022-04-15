package Networking.Tests;

import Networking.Core.NetworkingComponent;

import java.util.concurrent.TimeUnit;

public class TestListenFunctionality {
    public static void main(String argsp[]) throws InterruptedException {
        NetworkingComponent nc = new NetworkingComponent(32000);
        while(true){
            //nc.getSwarms();
            TimeUnit.SECONDS.sleep(4);
        }
    }
}
