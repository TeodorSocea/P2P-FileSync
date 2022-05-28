package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.MenuPack.ConsoleMenu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private Singleton mainData;
    CommandExecutor commandExecutor;

    public Main() {
        Input.confScanner();
        commandExecutor = new CommandExecutor();

        mainData = Singleton.getSingletonObject();
        mainData.setOperatingSystem(System.getProperty("os.name"));

        try {
            Singleton.loadSingletonData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testSerialization() {

        try {

            Singleton.getSingletonObject().setFolderToSyncPath("/downloads");
            Singleton.saveSingletonData();

            Singleton.loadSingletonData();
            System.out.println(Singleton.getSingletonObject().getFolderToSyncPath());
        } catch (IOException | NullPointerException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        System.exit(0);
    }

    public static void testSaveRecordToMasterFile() {

        List<SyncRecord> records = new LinkedList<>();
        records.add(new SyncRecord("a", true));
        records.add(new SyncRecord("b", true));
        records.add(new SyncRecord("c", true));
        int varA = 0;
        for (int i = 0; i < 1000000; i++) {
//            System.out.println("here");
            varA += 1;
        }
        records.add(new SyncRecord("d", true));
        records.add(new SyncRecord("e", true));
        records.add(new SyncRecord("f", true));
        Singleton.saveRecordsToMasterFile(records);

//        Am implementat metoda saveRecordsToMasterFile si metoda getSyncRecordWithPath ce vor fi de folos pentru sincronizarea corecta a fisierelor

        try {
            System.out.println(Singleton.getSyncRecordWithPath("a"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        for (Object obj: objs)
//            System.out.println(((SyncRecord) obj));

        System.exit(0);
    }


    public static void main(String[] args) {

//        testSerialization();
//        testSaveRecordToMasterFile();

        Main main = new Main();
        CommandExecutor commandExecutor = main.commandExecutor;

        System.out.println("App started! System detected: " + main.mainData.getOperatingSystem());
        String name = System.getProperty("user.name");
        System.out.println("System name: " + name);


        SignalReceiver signalReceiver = new SignalReceiver();

        new Thread(signalReceiver).start();

        ConsoleMenu.startToInteractWithTheUser();

    }
}
