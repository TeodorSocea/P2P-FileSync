package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon._UnitTests.ExceptionModule;
import Version_Control.VersionFileParser;
import Version_Control.Version_Control_Component;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetFilesFromVersionFile extends ExceptionModule implements Command {
    @Override
    public boolean execute() {

        VersionFileParser versionFileParser = Singleton.getSingletonObject().getVersionParser();
        Version_Control_Component vcc = Singleton.getSingletonObject().getVersion();

            Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();
            Path filePath = Paths.get(String.valueOf(folderPath), Singleton.getSingletonObject().VERSION_FILE_DATA_NAME);

            Path versionFile = filePath;

            byte[] versionFileBytes = BasicFileUtils.file2bytes(versionFile);

            if(versionFileBytes != null){
                String versionFileData = new String(versionFileBytes, StandardCharsets.UTF_8);
                vcc.setFisierVersiuni(versionFileData);
            }

        versionFileParser.setVersionFile(new JSONObject(vcc.getVersionFileData()));
        Singleton.getSingletonObject().getUserData().setVersionFileFiles(versionFileParser.getFiles());

        return true;
    }
}
