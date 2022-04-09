package Resident_Daemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// TODO: inlocuieste interfata cu clasa generala ce sta la baza implementarii command pattern-ului
interface ICommand {

    void execute(List<String> args);
}
class Option {
    private String whatToDisplay = null;
    private ICommand whatToExecute = null;

    public Option(String whatToDisplay, ICommand whatToExecute) {
        this.whatToDisplay = whatToDisplay;
        this.whatToExecute = whatToExecute;
    }

    public String getWhatToDisplay() {
        if (this.whatToExecute == null) {
            return "NULL";
        }
        return this.whatToDisplay;
    }

    public ICommand getWhatToExecute() {
        if (this.whatToExecute == null){
            return (args -> {
               System.out.println("NULL COMMAND!");
            });
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
            String consoleInput = getACmdIdxFromUser();
            List<String> args = splitInputIntoStrings(consoleInput);

            if (isAValidCmdIdx(args.get(0))) {

                Integer cmdIxd = Integer.parseInt(args.get(0));
                args.remove(0);
                userOptions.get(cmdIxd).getWhatToExecute().execute(args);
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

    private static String getACmdIdxFromUser() {

        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    private static void generateOptions() {

        userOptions.add(new Option("Synchronize", (args) -> {
            System.out.println(args);
        }));

        userOptions.add(new Option("Send files", (args) -> {
            System.out.println(args);
        }));

        userOptions.add(new Option("Exit", (args) -> {
            System.exit(0);
        }));
    }

    private static void display() {

        for (int indOpt = 0; indOpt < userOptions.size(); indOpt++) {
            System.out.println("[" + indOpt + "] " + userOptions.get(indOpt).getWhatToDisplay());
        }
    }
}
