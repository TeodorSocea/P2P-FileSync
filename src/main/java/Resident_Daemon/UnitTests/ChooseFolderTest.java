package Resident_Daemon.UnitTests;

import Resident_Daemon.BasicFileUtils;
import Resident_Daemon.Core.Singleton;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class ChooseFolderTest {
    @Test
    void testChooseFolder() {


        String folderPath = "src/main/java/Resident_Daemon/UnitTests/Media";

        String codeResult;
        if (BasicFileUtils.isDirectory(folderPath)) {
            Singleton.getSingletonObject().setFolderToSyncPath(folderPath);
            String path = Singleton.getSingletonObject().getFolderToSyncPath();
            codeResult = "The folder path is: " + path;
            assertEquals("The folder path is: src/main/java/Resident_Daemon/UnitTests/Media", codeResult);
            return;
        }
        System.out.println("The path is not a directory!");
    }

}