package Resident_Daemon;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.CommandsPack.Commands.LocalAPI.NewFile;
import Resident_Daemon.CommandsPack.Commands.Console.ConnectToIP;
import Resident_Daemon.CommandsPack.Commands.Console.CreateSwarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Option {
    private String whatToDisplay = null;
    private Command whatToExecute = null;
    private CommandExecutor commandExecutor;

    public Option(String whatToDisplay, Command whatToExecute) {
        this.whatToDisplay = whatToDisplay;
        this.whatToExecute = whatToExecute;
        commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
    }

    public String getWhatToDisplay() {
        if (this.whatToExecute == null) {
            return "NULL";
        }
        return this.whatToDisplay;
    }

    public Command getWhatToExecute() {
        if (this.whatToExecute == null){
            return () -> {
               System.out.println("NULL COMMAND!");
               return true;
            };
        }
        return this.whatToExecute;
    }
}

public class ConsoleMenu {

    private static List<Option> userOptions = new ArrayList<>();

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

        generateOptions();

        while (true) {

            display();
            String consoleInput = getInput();
            List<String> args = splitInputIntoStrings(consoleInput);

            if (isAValidCmdIdx(args.get(0))) {

                Integer cmdIxd = Integer.parseInt(args.get(0));
                args.remove(0);
                Command choosedComm = userOptions.get(cmdIxd).getWhatToExecute();

                Singleton.getSingletonObject().getCommandExecutor().ExecuteOperation(choosedComm);

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

        if (!(0 <= cmdIxd && cmdIxd < userOptions.size())) {
            return false;
        }

        return true;
    }

    private static String getInput() {

        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    private static void generateOptions() {

//

        userOptions.add(new Option("Synchronize", () -> {
            System.out.println("");
            return true;
        }));

        userOptions.add(new Option("Send files", () -> {
            System.out.println("");
            return true;
        }));

        userOptions.add(new Option("Create new file", new NewFile()));

        userOptions.add(new Option("Create new swarm(swarmId=\"18\", port:\"33531\")", new CreateSwarm()));

        userOptions.add(new Option("Connect to network", new ConnectToIP()));

        userOptions.add(new Option("Exit", () -> {
            System.exit(0);
            return true;
        }));
    }

    private static void display() {

        for (int indOpt = 0; indOpt < userOptions.size(); indOpt++) {
            System.out.println("[" + indOpt + "] " + userOptions.get(indOpt).getWhatToDisplay());
        }
    }
}
