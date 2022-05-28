package Resident_Daemon.Core;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.Utils.BasicFileUtils;
import Version_Control.Version_Control_Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Singleton
{
    //region Fields
    private static final String EXPORT_IMPORT_FOLDER = "data_singleton.txt";
    public static final String VERSION_FILE_DATA_NAME = "versionfile.version";

    private String currentPath;
    private String operatingSystem;
    private Path folderToSyncPath = null;
    private static Singleton singletonObject;

    private UserData userData;

    private NetworkingComponent networkingComponent;
    private Version_Control_Component version;
    CommandExecutor commandExecutor;
    //endregion

    private Singleton()
    {
        setCurrentPath();
        this.operatingSystem = "unknown";
        commandExecutor = new CommandExecutor();

        this.networkingComponent = new NetworkingComponent(30000);

        this.version = new Version_Control_Component();

        this.userData = new UserData();

    }

    public static Singleton getSingletonObject()
    {
        if (singletonObject == null)
        {
            singletonObject = new Singleton();
        }
        return singletonObject;
    }

    public static String filePathMasterSyncFile = "sync_files_evidence.data";

    public static void
    saveRecordsToMasterFile(List<SyncRecord> records) {

//        WRITE STUFF
//                Object obj = "abc";
//        String filePath = Singleton.filePathMasterSyncFile;
//        try {
//            List<Object> objs = new ArrayList<Object>();
//            SyncRecord syncRecord = new SyncRecord(Paths.get(".").toString(), true, 12);
//            objs.add(syncRecord);
//            BasicFileUtils.writeListOfObjectsToFileInOverwriteMode(objs, filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

///*
////        1. fetch the current records
//        List<Object> oldObjs = Singleton.getRecordsFromMasterFile();
        List<Object> objsToWrite = new ArrayList<>();
//
//        SyncRecord first = (SyncRecord)oldObjs.get(0);
//        SyncRecord sr = new SyncRecord(first.getFilePath(), first.getSynced(), first.getTimestampOfLastSync());
//        records.add(first);
//
////       2. merge the old records with the new ones
//        boolean found;
        for (SyncRecord record: records) {
            objsToWrite.add(record);
        }

//          3. call the write method
        String filePath = BasicFileUtils.GetMasterFilePath();
        try {
            BasicFileUtils.writeListOfObjectsToFileInOverwriteMode(objsToWrite, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

//*/
    }

    public static List<SyncRecord> getRecordsFromMasterFile() {

        List<SyncRecord> syncRecordList = new ArrayList<>();
        List<Object> objs = null;
        try {
            objs = BasicFileUtils.readListOfObjectsFromFile(BasicFileUtils.GetMasterFilePath());
            for(Object object : objs){
                syncRecordList.add((SyncRecord) object);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return syncRecordList;
    }
//    public static SyncRecord getSyncRecordWithPath(String path) throws FileNotFoundException {
//
//        List<Object> objs = Singleton.getRecordsFromMasterFile();
//        for(Object obj : objs)
//            if (((SyncRecord)obj).getFilePath().equals(path))
//                return (SyncRecord) obj;
//
//        throw new FileNotFoundException();
//    }


    //region Save & Load
    public static void saveSingletonData() throws IOException {
        saveSingletonData(EXPORT_IMPORT_FOLDER);
    }

    public static void loadSingletonData() throws IOException {
        Singleton.loadSingletonData(EXPORT_IMPORT_FOLDER);
    }

    //    TODO
    public static void saveSingletonData(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        DataOutputStream out = new DataOutputStream(fos);

//        folderToSyncPath
        out.writeUTF(getSingletonObject().getFolderToSyncPath().toString());

        out.writeUTF(getSingletonObject().getFolderToSyncPath().toString());
    }

    //    TODO
    public static void loadSingletonData(String fileName) throws IOException {
        //No savedData
        if(!(new File(fileName).isFile())){
            return;
        }

        FileInputStream fis = new FileInputStream(fileName);
        DataInputStream in = new DataInputStream(fis);

//        folderToSyncPath
        getSingletonObject().setFolderToSyncPath(in.readUTF());
        getSingletonObject().setFolderToSyncPath(in.readUTF());
    }
    //endregion

    //region Getters & Setters
    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public String getCurrentPath()
    {
        if( currentPath == null ) setCurrentPath();
        return currentPath;
    }

    public String getOperatingSystem()
    {
        return operatingSystem;
    }

    private void setCurrentPath()
    {
        try
        {
            this.currentPath = System.getProperty("user.dir");
        }
        catch (Exception e)
        {
            System.out.println("Current working directory exception: "+ e);
        }
    }

    public void setOperatingSystem(String os)
    {
        this.operatingSystem = os;
    }

    public Path getFolderToSyncPath() throws NullPointerException{
        if (folderToSyncPath == null) {
            throw new NullPointerException("FolderToSyncPath is null!");
        }
        return folderToSyncPath;
    }

    public void setFolderToSyncPath(String folderToSyncPath) throws InvalidPathException {

        if (!Files.exists(Path.of(folderToSyncPath)) || folderToSyncPath.equals("")) {
            throw new InvalidPathException(folderToSyncPath, "Trying to set an invalid folder path!");
        }
        this.folderToSyncPath = Path.of(folderToSyncPath);
    }

    public NetworkingComponent getNetworkingComponent() {
        return networkingComponent;
    }

    public UserData getUserData() {
        return userData;
    }

    public Version_Control_Component getVersion() {
        return version;
    }

    //endregion

}
