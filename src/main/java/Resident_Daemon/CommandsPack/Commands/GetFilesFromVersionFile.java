package Resident_Daemon.CommandsPack.Commands;

import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon._UnitTests.ExceptionModule;
import Version_Control.VersionFileParser;
import Version_Control.Version_Control_Component;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class GetFilesFromVersionFile extends ExceptionModule implements Command {
    private int swarmID;

    public GetFilesFromVersionFile(int swarmID) {
        this.swarmID = swarmID;
    }

    @Override
    public boolean execute() {

        VersionFileParser versionFileParser = Singleton.getSingletonObject().getVersionParser();
        Version_Control_Component vcc = Singleton.getSingletonObject().getVersion_control_component();

        String versionFileData = BasicFileUtils.GetIfExistsVersionFileData(swarmID);
        vcc.setFisierVersiuni(versionFileData);
        versionFileParser.setVersionFile(new JSONObject(vcc.getVersionFileData()));
        Singleton.getSingletonObject().getUserData().setVersionFileFiles(versionFileParser.getFiles());

        System.out.println(Singleton.getSingletonObject().getUserData().getVersionFileFiles());

        return true;
    }
}
