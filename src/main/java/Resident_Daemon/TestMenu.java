package Resident_Daemon;
import Networking.Networking_Component;

import java.io.IOException;
import java.util.Scanner;

public class TestMenu {

    public static void main(String[] args) throws IOException {
        Networking_Component nc = new Networking_Component();
        nc.connect("127.0.0.1");
        //ConsoleMenu.startToInteractWithTheUser();
    }
}
