package Resident_Daemon.PackageOS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class SpecificAPI extends DetectOS{
    public void WindowsCommands() {
        String command="netstat"; // aici punem ce comenzi vrem sa executam pe sistemele de operare windows
        try {
            Process process = Runtime.getRuntime().exec(command);
            System.out.println("the output stream is "+process.getOutputStream());
            BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = reader.readLine()) != null){
                System.out.println("The inout stream is " + s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void MacOSCommands() {

    }
    public void UnixCommands() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("pwd"); // comenzi linux
            //Process process = Runtime.getRuntime().exec("cmd /c dir"); //for Windows

            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            process.destroy();
        }
    }
    public void runOSActions(){
        String os = getOperatingSystem().toLowerCase(Locale.ROOT);
        System.out.println(os);
        if (os.contains("win")){
            //Operating system is based on Windows
            WindowsCommands();
        }
        else if (os.contains("mac")){
            //Operating system is Apple OSX based
            MacOSCommands();
        }
        else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){
            //Operating system is based on Linux/Unix/*AIX
            UnixCommands();
        }
    }
}
