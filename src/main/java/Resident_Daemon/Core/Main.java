package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.MenuPack.ConsoleMenu;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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

    public static void testSaveRecordToMasterFile() throws IOException {

        Singleton.getSingletonObject().setFolderToSyncPath("C:\\Users\\tnae\\Desktop\\Fac");
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

//        List<SyncRecord> records = new LinkedList<>();

//

        List<SyncRecord> list = new LinkedList<>();
        for(var file : GetTextFiles.getTextFiles(folderPath).entrySet()){
            var sr = new SyncRecord (file.getKey().toString(), true);
            list.add(sr);
        }
        BasicFileUtils.writeRecordsToMasterFileOverwrite(list);
//        writeRecordsToMasterFileOverwrite

//        Am implementat metoda saveRecordsToMasterFile si metoda getSyncRecordWithPath ce vor fi de folos pentru sincronizarea corecta a fisierelor

        var syncRecordList = BasicFileUtils.readRecordsFromMasterFile();
        for(var c : syncRecordList){
            System.out.println("fileRelPath: " + c.getFileRelPath());
            System.out.println("timestamp: " + c.getLastModifiedTimeStamp());
        }

//        for (Object obj: objs)
//            System.out.println(((SyncRecord) obj));

        System.exit(0);
    }

    public static void testSaveRecordToMasterFileFacutDeBalan() throws IOException {

        Singleton.getSingletonObject().setFolderToSyncPath(".");
        System.out.println(BasicFileUtils.GetMasterFilePath());

        var sr = new SyncRecord("b", true, 12);
        List<SyncRecord> list = new ArrayList<>();
        list.add(sr);
        BasicFileUtils.writeRecordsToMasterFileOverwrite(list);
//        var records = BasicFileUtils.readRecordsFromMasterFile();

        System.exit(1);
    }

    public static void main(String[] args) {

//        testSerialization();
        try {
            testSaveRecordToMasterFileFacutDeBalan();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
