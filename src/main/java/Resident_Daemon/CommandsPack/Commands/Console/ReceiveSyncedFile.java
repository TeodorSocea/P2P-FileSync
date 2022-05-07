package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.Core.Singleton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class ReceiveSyncedFile implements Command {

    private String getFilePath(String data){
        return data.substring(0, data.indexOf("!"));
    }

    private String getContent(String data){
        return data.substring(data.indexOf("!") + 1);
    }

    @Override
    public boolean execute() {

        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();
        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        if (folderPath == null){
            System.out.println("Choose the folder to sync first!");
            return false;
        }

        Scanner input = new Scanner(System.in);

        System.out.println("Input swarm's ID: ");

        String sID = input.nextLine();
        int swarmID;

        try {
            swarmID = Integer.parseInt(sID);
        } catch (NumberFormatException e){
            System.out.println("Invalid ID!");
            return false;
        }

        Map<Integer, byte[]> dataMap = networkingComponent.receiveData(swarmID);

        System.out.println("Received changes:\n");

        int i = 0;

        for(int IP : dataMap.keySet()){
            String data = new String(dataMap.get(IP));
            String filePath = getFilePath(data);

            System.out.printf("[%d] %d : %s",i, IP, filePath);

            i++;
        }
        System.out.println("");

        System.out.println("\nChoose the index to apply changes: ");

        String sIndex = input.nextLine();
        int index;

        try {
            index = Integer.parseInt(sIndex);
        } catch (NumberFormatException e){
            System.out.println("Invalid index!");
            return false;
        }

        int choosedIP = -1;
        i = 0;

        for (int IP : dataMap.keySet()){
            if(i == index){
                choosedIP = IP;
                System.out.println(i + "_" + IP);
            }
            i++;
        }

        System.out.println("Index choosed: " + index + ", data_ID choosed: " + choosedIP);

        if(choosedIP == -1){
            System.out.println("Error while searching ip!");
            return false;
        }

        String receivedData = new String(dataMap.get(choosedIP));

        String receivedPath = getFilePath(receivedData);
        Path filePath = Paths.get(folderPath, receivedPath);

        String whatToWrite = getContent(receivedData);

        try {
            Files.writeString(filePath, whatToWrite);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }
}
