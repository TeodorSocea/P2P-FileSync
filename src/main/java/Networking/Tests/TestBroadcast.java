package Networking.Tests;

import Networking.Core.NetworkingComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TestBroadcast {
    public static void main(String args[]) throws InterruptedException, IOException {
        NetworkingComponent networkingComponent = new NetworkingComponent(30000);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        while(true){
            System.out.println("1 - Print IPs\n2 - Print Sockets\n3 - Connect to IP\n4 - Create New Swarm\n5 - Invite to swarm\n6 - Print Invitations\n7 - Respond to invitation\n8 - Print Swarms");
            String input = reader.readLine();
            if(Objects.equals(input, "1")){
                networkingComponent.printCommonIPPool();
            }else if(Objects.equals(input, "2")){
                networkingComponent.printCommonSocketPool();
            }else if(Objects.equals(input, "3")){
                String ip = reader.readLine();
                networkingComponent.connectToIP(ip);
            }else if(Objects.equals(input, "4")){
                networkingComponent.createNewSwarm();
            }else if(Objects.equals(input, "5")){
                String ip = reader.readLine();
                String id = reader.readLine();
                networkingComponent.inviteIPToSwarm(ip, Integer.parseInt(id));
            }else if(Objects.equals(input, "6")){
                networkingComponent.printInvitations();
            }else if(Objects.equals(input, "7")){
                String index = reader.readLine();
                String reponse = reader.readLine();
                networkingComponent.respondToInvitationToSwarm(Integer.parseInt(index), Boolean.parseBoolean(reponse));
            }else if(Objects.equals(input, "8")){
                networkingComponent.printSwarms();
            }
        }
    }
}
