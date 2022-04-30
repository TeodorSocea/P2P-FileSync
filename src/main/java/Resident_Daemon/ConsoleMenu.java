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

    public static int pageNumber = 0;
    private static List<List<Option>> userOptions = new ArrayList<>();

    private static void init() {

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

                Integer cmdIxd = Integer.parseInt(args.get(0));
                args.remove(0);
                List<Option> page = userOptions.get(pageNumber);
                Command choosedComm = page.get(cmdIxd).getWhatToExecute();

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

//      pagina 0

        List<Option> page0 = userOptions.get(0);
        page0.add(new Option("Disconnect", () -> {
            ConsoleMenu.pageNumber = (ConsoleMenu.pageNumber + 1) % 2;
            return true;
        }));

        page0.add(new Option("Exit", () -> {
            System.exit(0);
            return true;
        }));

//        pagina 1

        List<Option> page1 = userOptions.get(1);

        page1.add(new Option("Connect", () -> {
            ConsoleMenu.pageNumber = (ConsoleMenu.pageNumber + 1) % 2;
            return true;
        }));

        page1.add(new Option("Synchronize", () -> {
            System.out.println("");
            return true;
        }));

        page1.add(new Option("Send files", () -> {
            System.out.println("");
            return true;
        }));

        page1.add(new Option("Create new file", new NewFile()));

        page1.add(new Option("Create new swarm(swarmId=\"18\", port:\"33531\")", new CreateSwarm()));

        page1.add(new Option("Connect to network", new ConnectToIP()));

        page1.add(new Option("Exit", () -> {
            System.exit(0);
            return true;
        }));
    }

    private static void display() {

        List<Option> page = userOptions.get(pageNumber);
        for (int indOpt = 0; indOpt < page.size(); indOpt++) {
            System.out.println("[" + indOpt + "] " + page.get(indOpt).getWhatToDisplay());
        }
    }
}
