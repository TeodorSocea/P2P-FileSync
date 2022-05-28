package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.MenuPack.ConsoleMenu;
import Resident_Daemon.Utils.GetTextFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
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

        Singleton.getSingletonObject().setFolderToSyncPath("C:\\Users\\tnae\\Desktop\\Fac");
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        List<SyncRecord> records = new LinkedList<>();

        for(var file : GetTextFiles.getTextFiles(folderPath).entrySet()){
            records.add(new SyncRecord (file.getKey().toString(), true));
        }

        Singleton.saveRecordsToMasterFile(records);

//        Am implementat metoda saveRecordsToMasterFile si metoda getSyncRecordWithPath ce vor fi de folos pentru sincronizarea corecta a fisierelor

        var syncRecordList = Singleton.getRecordsFromMasterFile();
        for(var c : syncRecordList){
            System.out.println("fileRelPath: " + c.getFileRelPath());
            System.out.println("timestamp: " + c.getLastModifiedTimeStamp());
        }

//        for (Object obj: objs)
//            System.out.println(((SyncRecord) obj));

        System.exit(0);
    }


    public static void main(String[] args) {

//        testSerialization();
        testSaveRecordToMasterFile();

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
