package Resident_Daemon.CommandsPack.Commands.Console;

import Resident_Daemon.BasicFileUtils;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.Singleton;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReceiveSyncedFile implements Command {

    private String getFilePath(String data){
        return data.substring(0, data.indexOf("!"));
    }

    private String getContent(String data){
        return data.substring(data.indexOf("!") + 1);
    }

    @Override
    public boolean execute() {

        // Need network

        String receivedData =  "interesting.txt!File's bytes:\nA lot of different and interesting bytes.";

        String receivedPath = getFilePath(receivedData);
        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath();
        Path filePath = Paths.get(folderPath, receivedPath);

        String whatToWrite = getContent(receivedData);

        try {
            Files.writeString(filePath, whatToWrite);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }
}
