package Resident_Daemon;


public class Singleton
{
    private String currentPath;
    private String operatingSystem;
    private static Singleton singletonObject;


    public static Singleton getSingletonObject()
    {
        if (singletonObject == null)
        {
            singletonObject = new Singleton();
        }
        return singletonObject;
    }

    public String getCurrentPath()
    {
        if( currentPath == null ) setCurrentPath();
        return currentPath;
    }

    public String getOperatingSystem()
    {
        if ( operatingSystem == null ) setOperatingSystem();
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

    private Singleton()
    {

    }
}
