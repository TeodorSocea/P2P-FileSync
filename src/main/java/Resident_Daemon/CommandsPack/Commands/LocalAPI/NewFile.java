package Resident_Daemon.CommandsPack.Commands.LocalAPI;

import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.PackageOS.DetectOS;
import Resident_Daemon.PackageOS.LocalAPI;

import java.util.Locale;

public class NewFile extends LocalAPI implements Command {

    @Override
    public boolean execute() {
        String os = new DetectOS().getOperatingSystem().toLowerCase(Locale.ROOT);
        if(os.contains("win")) {
            WindowsCommands("\\MoruzWindows.txt");
        }
        else if(os.contains("mac"))
        {
            MacOSCommands("\\MoruzMac.txt");
        }
        else if(os.contains("nix") || os.contains("linux"))
        {
            UnixCommands("\\MoruzLinux.txt");
        }
        return false;
    }
}
