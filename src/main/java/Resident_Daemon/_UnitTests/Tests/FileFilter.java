package Resident_Daemon._UnitTests.Tests;

import Resident_Daemon.Utils.BasicFileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileFilter {
    public static boolean isTextPlainFile(Path filePath) {

        try {
            String mimeType = Files.probeContentType(filePath);
//            System.out.println(mimeType);
            return mimeType.equals("text/plain");
        } catch (IOException | NullPointerException e) {
            return false;
        }


    }

    public static boolean isAsciiText(Path filePath) throws IOException {

        byte[] bytes = BasicFileUtils.file2bytes(filePath);
        if(bytes == null) return false;

        int x = 0;
        short bin = 0;

        for (byte thisByte : bytes) {
            char it = (char) thisByte;
            if (!Character.isWhitespace(it) && Character.isISOControl(it)) {

                bin++;
            }
            if (bin >= 5) {
                return false;
            }
            x++;
        }
        return true;
    }


    public static void main(String[] args) {
        Path filePath = Path.of("C:\\Users\\Theodor\\Desktop\\FolderToSyc\\app.js");
        System.out.println(isTextPlainFile(filePath));
        try {
            System.out.println(isAsciiText(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
