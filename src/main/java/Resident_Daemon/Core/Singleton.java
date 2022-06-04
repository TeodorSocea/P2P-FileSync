package Resident_Daemon.Core;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Version_Control.VersionFileParser;
import Version_Control.Version_Control_Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Singleton
{
    //region Fields
    private static final String EXPORT_IMPORT_FOLDER = "data_singleton.txt";
    private  VersionFileParser versionParser;

    private String currentPath;
    private String operatingSystem;
    private Path folderToSyncPath = null;
    private static Singleton singletonObject;

    private UserData userData;

    private NetworkingComponent networkingComponent;
    private Version_Control_Component version_control_component;
    CommandExecutor commandExecutor;
    //endregion

    private Singleton()
    {
        setCurrentPath();
        this.operatingSystem = "unknown";
        commandExecutor = new CommandExecutor();

        this.networkingComponent = new NetworkingComponent(30000);

        this.version_control_component = new Version_Control_Component();
        this.versionParser = new VersionFileParser();

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

    public Version_Control_Component getVersion_control_component() {
        return version_control_component;
    }

    public VersionFileParser getVersionParser() {
        return versionParser;
    }

    //endregion

}
