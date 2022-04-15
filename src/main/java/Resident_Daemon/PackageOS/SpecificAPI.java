package Resident_Daemon.PackageOS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class SpecificAPI extends DetectOS{
    public void CommandsProcessing (String command)
    {
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
    public void WindowsCommands() {
        String command="help"; // aici punem ce comenzi vrem sa executam pe sistemele de operare windows
        CommandsProcessing(command);
    }
    public void MacOSCommands() {
        String command="comenzi pt mac";
        CommandsProcessing(command);
    }
    public void UnixCommands() {
        String command="comenzi pt linux";
        CommandsProcessing(command);
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
