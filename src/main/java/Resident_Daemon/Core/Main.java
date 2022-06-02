package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.MenuPack.ConsoleMenu;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        Singleton.getSingletonObject().setFolderToSyncPath("C:\\Users\\tnae\\Desktop\\Fac");
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();
        System.out.println(BasicFileUtils.GetMasterFilePath());
        System.out.println(Singleton.getSingletonObject().getFolderToSyncPath());

        List<SyncRecord> list = new ArrayList<>();
        for(var c : GetTextFiles.getTextFiles(folderPath).entrySet()){
            SyncRecord syncRecord = new SyncRecord(c.getKey().toString(), true);
            System.out.println(c.getKey());
            list.add(syncRecord);
        }
        BasicFileUtils.writeRecordsToMasterFileOverwrite(list);

        var listRec = BasicFileUtils.readRecordsFromString(
                "2\n" +
                "b - copy!true!12\n" +
                "b !true!12\n");

        System.out.println(listRec);

        var records = BasicFileUtils.readRecordsFromMasterFile();
        System.out.println(records);

        System.exit(1);
    }

    public static void testSaveRecordToMasterV2Read() {
        String d = BasicFileUtils.MASTER_FILE_DELIM;
        var listRec = BasicFileUtils.readRecordsFromString(
                "3" + d +
                "b - copy" + d + "true" + d + "12" + d +
                "12b " + d + "true" + d + "12" + d +
                "b - \ncopy" + d + "true" + d + "12");

        System.out.println(listRec);
    }
    public static void testSaveRecordToMasterV2Write() throws IOException {

//        System.out.println("here");

//        b true 12

        var list = new LinkedList<SyncRecord>();
        var sr = new SyncRecord("b", true, 12);
        list.add(sr);
        list.add(sr);

        Singleton.getSingletonObject().setFolderToSyncPath(".");
        BasicFileUtils.writeRecordsToMasterFileOverwrite(list);

        var listRead = BasicFileUtils.readRecordsFromMasterFile();
        System.out.println(list.equals(listRead));
    }

        public static void testSaveRecordToMasterV2() throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        scanner.useDelimiter(";\n");
//        scanner.useDelimiter(",|\\n");

//            testSaveRecordToMasterV2Read();
            testSaveRecordToMasterV2Write();

            System.exit(1);
    }

    public static void main(String[] args) throws IOException {

//        testScanner();

//        testSerialization();
//        testSaveRecordToMasterV2();

        Main main = new Main();
        CommandExecutor commandExecutor = main.commandExecutor;

        String name = System.getProperty("user.name");


        SignalReceiver signalReceiver = new SignalReceiver();

        new Thread(signalReceiver).start();

        ConsoleMenu.startToInteractWithTheUser();

    }
}
