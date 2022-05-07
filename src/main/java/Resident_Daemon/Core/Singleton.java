package Resident_Daemon.Core;


import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.CommandExecutor;

public class Singleton
{
    static public int port = 30000;

    private String currentPath;
    private String operatingSystem;
    private String folderToSyncPath;
    private static Singleton singletonObject;

    private NetworkingComponent networkingComponent;
    CommandExecutor commandExecutor;

    private Singleton()
    {
        setCurrentPath();
        this.operatingSystem = "unknown";
        commandExecutor = new CommandExecutor();

        System.out.println("------");
        System.out.println(this.port);
        this.networkingComponent = new NetworkingComponent(this.port);
//        this.networkingComponent = new NetworkingComponent(0);
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

    public String getFolderToSyncPath() {
        return folderToSyncPath;
    }

    public void setFolderToSyncPath(String folderToSyncPath) {
        this.folderToSyncPath = folderToSyncPath;
    }

    public NetworkingComponent getNetworkingComponent() {
        return networkingComponent;
    }

    //endregion

}
