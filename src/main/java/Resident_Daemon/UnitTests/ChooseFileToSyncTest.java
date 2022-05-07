package Resident_Daemon.UnitTests;


import Resident_Daemon.BasicFileUtils;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.*;

class ChooseFileToSyncTest {

    @Test
    void testFileMatching() {
        String folderPath = "src/main/java/Resident_Daemon/UnitTests/Media";

        System.out.println("The folder path is: " + folderPath);

        String fileRelativePath = "ceva.txt";

        Path filePath = Paths.get(folderPath, fileRelativePath);

        if (!Files.isRegularFile(filePath)) return;

        byte[] out = BasicFileUtils.file2bytes(filePath);
        String fileContent = new String(out, StandardCharsets.UTF_8);

        String fromFileOLD = fileRelativePath + "!" + fileContent;
        String dataShouldBe = "ceva.txt!niste bytes obositi";

        assertEquals(dataShouldBe, fromFileOLD);
    }
}