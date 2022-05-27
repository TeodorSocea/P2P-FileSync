package Resident_Daemon.Core;


import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.CommandExecutor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Singleton
{
    // abcdefd
    private static final String EXPORT_IMPORT_FOLDER = "data_singleton.txt";

    private String currentPath;
    private String operatingSystem;
    private Path folderToSyncPath = null;
    private static Singleton singletonObject;

    private NetworkingComponent networkingComponent;
    CommandExecutor commandExecutor;

    private Singleton()
    {
        setCurrentPath();
        this.operatingSystem = "unknown";
        commandExecutor = new CommandExecutor();
        this.networkingComponent = new NetworkingComponent(30000);
    }

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
        FileInputStream fis = new FileInputStream(fileName);
        DataInputStream in = new DataInputStream(fis);

//        folderToSyncPath
        getSingletonObject().setFolderToSyncPath(in.readUTF());
        getSingletonObject().setFolderToSyncPath(in.readUTF());
    }

    public static Singleton getSingletonObject()
    {
        if (singletonObject == null)
        {
            singletonObject = new Singleton();
        }
        return singletonObject;
    }

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

    public Path getFolderToSyncPath() {
        if (folderToSyncPath == null) {
            throw new NullPointerException("FolderToSyncPath is null!");
        }
        return folderToSyncPath;
    }

    public void setFolderToSyncPath(String folderToSyncPath) throws FileNotFoundException {

        if (Files.notExists(Path.of(folderToSyncPath))) {
            throw new FileNotFoundException("Trying to set a folder to sync that does not exits!");
        }
        this.folderToSyncPath = Path.of(folderToSyncPath);
    }

    public NetworkingComponent getNetworkingComponent() {
        return networkingComponent;
    }

    //endregion

}
