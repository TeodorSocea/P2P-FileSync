package Resident_Daemon.PackageOS;
public class DetectOS {
    public String getOperatingSystem() {
        return System.getProperty("os.name");
    }

}
