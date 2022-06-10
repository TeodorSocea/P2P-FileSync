package Resident_Daemon.CommandsPack.Commands;


import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon._UnitTests.ExceptionModule;
import Version_Control.VersionFileParser;
import Version_Control.Version_Control_Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


public class Rollback extends ExceptionModule implements Command {
    private String fileName;
    private long timestamp;
    private Integer swarmID;

    public Rollback(String fileName, long timestamp, Integer swarmID) {
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.swarmID = swarmID;
    }

    private void UpdateFile(String fileRelName, String fileData) {
        try {
            BasicFileUtils.WriteFileToFolder(swarmID, fileRelName, timestamp, fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void UpdateVersionFile(String fileData) {
        BasicFileUtils.WriteVersionFile(swarmID, fileData);
    }

    @Override
    public boolean execute() {

        Version_Control_Component vc = Singleton.getSingletonObject().getVersion_control_component();

        Path fileAbsolutePath = Path.of(BasicFileUtils.GetAbsolutePath_FromRelative(swarmID, fileName));
        byte[] fileBytes = BasicFileUtils.file2bytes(fileAbsolutePath);
        String fileContent = new String(fileBytes, StandardCharsets.UTF_8);

        vc.setRollbackFile(fileContent);
        vc.setRollbackFileName(fileName);
        vc.rollbackFile(timestamp);

        timestamp = BasicFileUtils.GetCurrentTimeStamp();
        UpdateFile(fileName, vc.getRollbackFile());
        UpdateVersionFile(vc.getVersionFileData());




        return true;
    }
}
