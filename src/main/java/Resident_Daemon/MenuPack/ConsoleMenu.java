package Resident_Daemon.MenuPack;

import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Console.*;
import Resident_Daemon.Core.Singleton;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    public static int pageNumber = 0;
    private static List<List<Option>> userOptions = new ArrayList<>();

    private static void init() {
        try {
            Path folderToSyncPath = Singleton.getSingletonObject().getFolderToSyncPath();
            pageNumber = 1;
        } catch (NullPointerException e) {
            pageNumber = 0;
        }


        userOptions.add(new ArrayList<>());
        userOptions.add(new ArrayList<>());

        generateOptions();
    }

    private static List<String> splitInputIntoStrings(String consoleInput) {

        List<String> args = new ArrayList<>();

        boolean firstNonSpaceReached = false;
        boolean isReadingAWord = false;
        StringBuilder currArg = null;

        for (int indChr = 0; indChr < consoleInput.length(); indChr++) {

            char currChr = consoleInput.toCharArray()[indChr];
            if (!firstNonSpaceReached && currChr != ' ') {
                firstNonSpaceReached = true;
                isReadingAWord = true;
                currArg = new StringBuilder();
                currArg.append(currChr);
                continue;
            }

//            (indChr > 0 100%)
            char prevChr = consoleInput.toCharArray()[indChr - 1];
            if (prevChr == ' ' && currChr != ' ') {
                isReadingAWord = true;
                currArg = new StringBuilder();
            }

            if (isReadingAWord && currChr == ' ') {
                isReadingAWord = false;
                args.add(currArg.toString());
            }

            if (isReadingAWord) {
                currArg.append(currChr);
            }
        }

        if (isReadingAWord) {
            args.add(currArg.toString());
        }

        return args;
    }

    public static void startToInteractWithTheUser() {

        init();

        while (true) {

            display();
            String consoleInput = getInput();
            List<String> args = splitInputIntoStrings(consoleInput);

            if (isAValidCmdIdx(args.get(0))) {
                Integer cmdIxd;
                cmdIxd = Integer.parseInt(args.get(0));

                args.remove(0);
                List<Option> page = userOptions.get(pageNumber);
                Command choosedComm = page.get(cmdIxd).getWhatToExecute().run();

                if(choosedComm != null){
                    boolean valid = Singleton.getSingletonObject().getCommandExecutor().ExecuteOperation(choosedComm);

                    if(choosedComm instanceof ConsolePageSwitch && valid){
                        ((ConsolePageSwitch) choosedComm).ChangePage();
                    }
                }

            } else {
                System.out.println("Invalid input!");
            }

        }
    }

    private static boolean isAValidCmdIdx(String consoleInput) {

        Integer cmdIxd = null;
        try {
            cmdIxd = Integer.parseInt(consoleInput);
        } catch (NumberFormatException exception) {
            return false;
        }

        List<Option> page = userOptions.get(pageNumber);
        if (!(0 <= cmdIxd && cmdIxd < page.size())) {
            return false;
        }

        return true;
    }

    private static String getInput() {

        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    private static void generateOptions() {
        List<Option> page;

//      pagina 0
        page = userOptions.get(0);

        page.add(new Option("Choose folder to sync", new AuxChooseFolder()));

//      pagina 1
        page = userOptions.get(1);

        page.add(new Option("Create new swarm", new AuxCreateSwarm()));

        page.add(new Option("Send to Synchronize file", new AuxChooseFileToSync()));

        page.add(new Option("Print IPs", new AuxPrintIPs()));

        page.add(new Option("Invite to swarm", new AuxInviteToSwarm()));

        page.add(new Option("Print invitations", new AuxPrintInvitations()));

        page.add(new Option("Respond to invitation", new AuxRespondToInvitation()));

        page.add(new Option("Print swarms", new AuxPrintSwarms()));

        page.add(new Option("Sync swarm (send request)", new AuxSyncSwarm()));

        page.add(new Option("Print requests", new AuxPrintFullFilledRequests()));

//        page.add(new Option("Disconnect", () -> {
//            ConsoleMenu.pageNumber = (ConsoleMenu.pageNumber - 1) % 2;
//            return null;
//        }));

        page.add(new Option("Exit", new AuxExitApp()));
    }

    private static void display() {

        List<Option> page = userOptions.get(pageNumber);
        for (int indOpt = 0; indOpt < page.size(); indOpt++) {
            System.out.println("[" + indOpt + "] " + page.get(indOpt).getWhatToDisplay());
        }
    }
}
