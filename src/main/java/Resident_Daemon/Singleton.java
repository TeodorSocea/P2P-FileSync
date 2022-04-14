package Resident_Daemon;

import java.nio.file.Path;
import java.sql.SQLOutput;

public class Singleton
{
    private String currentPath;
    private String operatingSystem;

    private static Singleton singletonObject = new Singleton();

    public static Singleton getSingletonObject()
    {
        return singletonObject;
    }

    public String getCurrentPath()
    {
        return currentPath;
    }

    public String getOperatingSystem()
    {
        return operatingSystem;
    }

    private void setPath()
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

    private void setOperatingSystem()
    {
        try
        {
            this.operatingSystem = System.getProperty("os.name");
        }
        catch (Exception e)
        {
            System.out.println("Operating System exception: " + e );
        }
    }

    Singleton()
    {
        setPath();
        setOperatingSystem();
    }
}
