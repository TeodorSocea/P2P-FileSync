package Resident_Daemon.PackageOS;
public class DetectOS {
    public String getOperatingSystem() {
        String os = System.getProperty("os.name");
        // System.out.println("Using System Property: " + os);
        return os;
    }

}
