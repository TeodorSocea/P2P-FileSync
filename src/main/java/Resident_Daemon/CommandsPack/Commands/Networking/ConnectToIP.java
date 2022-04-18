package Resident_Daemon.CommandsPack.Commands.Networking;

import Networking.Core.NetworkingComponent;
import Networking.Messages.ConnectMessage;
import Resident_Daemon.CommandsPack.Commands.Command;

import java.io.IOException;

public class ConnectToIP implements Command {

    @Override
    public boolean execute() {

        NetworkingComponent nc = new NetworkingComponent(32001);
        nc.start();

        try {
            nc.connect("192.168.1.220");
            nc.handleMessage(new ConnectMessage(18),null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
