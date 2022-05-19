package Resident_Daemon.Core;

import Networking.Core.NetworkingComponent;

import java.util.concurrent.TimeUnit;

public class SignalReceiver implements Runnable {
    UserData userData = Singleton.getSingletonObject().getUserData();
    NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

    @Override
    public void run() {
//        //seconds
//        long notificationTime = 5;
//
//        while(true){
//            if(userData.isConnected()){
//                for(var pair : networkingComponent.getFulfilledRequests()){
//                    System.out.println(pair);
//                }
//            }
//            try {
//                TimeUnit.SECONDS.sleep(notificationTime);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }
}
