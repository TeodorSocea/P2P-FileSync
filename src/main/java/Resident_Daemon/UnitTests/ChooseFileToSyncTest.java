package Resident_Daemon.UnitTests;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.BasicFileUtils;
import Resident_Daemon.Core.Singleton;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ChooseFileToSyncTest {

    @Test
    void testFileMatching(){
        String folderPath = "src/main/java/Resident_Daemon/UnitTests/Media";

        System.out.println("The folder path is: " + folderPath);

        String fileRelativePath = "ceva.txt";


        Path filePath = Paths.get(folderPath, fileRelativePath);

        if (!Files.isRegularFile(filePath)) return;

        byte[] out = BasicFileUtils.file2bytes(filePath);
        String fileContent = new String(out, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder(fileRelativePath + "!" + fileContent);

        String fromFileOLD = stringBuilder.toString();
        String dataShouldBe = "ceva.txt!niste bytes obositi";

        assertEquals(dataShouldBe, fromFileOLD);
    }
}