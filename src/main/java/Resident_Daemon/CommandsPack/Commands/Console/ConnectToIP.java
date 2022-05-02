package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Networking.Messages.ConnectMessage;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.ConsoleMenu;

import java.io.IOException;
import java.util.Scanner;

public class ConnectToIP implements Command {

    @Override
    public boolean execute() {
        Scanner input = new Scanner(System.in);

        System.out.println("Input port: ");
        String sPort = input.nextLine();
        try {
            int port = Integer.parseInt(sPort);
            System.out.println("Input ip: ");
            String ip = input.nextLine();

            NetworkingComponent nc = new NetworkingComponent(port);
            nc.start();

            try {
                nc.connect(ip);
                nc.handleMessage(new ConnectMessage(18),null);

                ConsoleMenu.pageNumber += 1 % 2;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            System.out.println("You can enter only digits!");
        }



        return false;
    }
}
