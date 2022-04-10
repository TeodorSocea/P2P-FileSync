package Networking;

import java.util.concurrent.TimeUnit;

public class TestListenFunctionality {
    public static void main(String argsp[]) throws InterruptedException {
        Networking_Component nc = new Networking_Component(32000);
        while(true){
            nc.getSwarms();
            TimeUnit.SECONDS.sleep(4);
        }
    }
}
