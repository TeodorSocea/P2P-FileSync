package Resident_Daemon.CommandsPack.Commands;

import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;
import Version_Control.VersionFileParser;

public class GetChangesFromTimestampVersionFile extends ExceptionModule implements Command {
    private String fileName;
    private String timestamp;

    public GetChangesFromTimestampVersionFile(String fileName, String timestamp) {
        this.fileName = fileName;
        this.timestamp = timestamp;
    }

    @Override
    public boolean execute() {
        VersionFileParser versionFileParser = Singleton.getSingletonObject().getVersionParser();
        Singleton.getSingletonObject().getUserData().setChangesFileVersionFile(versionFileParser.getChangesOfFile(this.fileName, this.timestamp));


        return true;
    }
}
