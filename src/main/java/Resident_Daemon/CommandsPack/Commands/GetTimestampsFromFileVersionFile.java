package Resident_Daemon.CommandsPack.Commands;


import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;
import Version_Control.VersionFileParser;


public class GetTimestampsFromFileVersionFile  extends ExceptionModule implements Command {
    private String fileName;

    public GetTimestampsFromFileVersionFile(String fileName) {
    this.fileName = fileName;
    }

    @Override
    public boolean execute() {

        VersionFileParser versionFileParser = Singleton.getSingletonObject().getVersionParser();
        Singleton.getSingletonObject().getUserData().setTimestampVersionFileFiles(versionFileParser.getTimestampsOfFile(this.fileName));


        return true;
    }
}
