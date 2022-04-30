package Resident_Daemon;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class TestFileCopyOperation {

    public static void main(String[] args) {

        String pathSrcStr = "src/main/java/Resident_Daemon/fisier.txt";
        String pathDestStr = "src/main/java/Resident_Daemon/fisier.txt";

        Path pathSrc = FileSystems.getDefault().getPath(pathSrcStr);
        Path pathDest = FileSystems.getDefault().getPath(pathDestStr);

        boolean success = BasicFileUtils.copyFile(pathSrc, pathDest);
        System.out.println("Success file copy? " + success);

//        System.out.println(pathDest);
        String whatToWrite = "abc";
        byte[] src = whatToWrite.getBytes(StandardCharsets.UTF_8);
        success = BasicFileUtils.bytes2file(src, pathDest);
        System.out.println("Success byte[] to file? " + success);

        byte[] out = BasicFileUtils.getBytesFromFile(pathDest);
        String fileContent = new String(out, StandardCharsets.UTF_8);
        System.out.println("Success file to string? " + whatToWrite.equals(fileContent));
    }
}
