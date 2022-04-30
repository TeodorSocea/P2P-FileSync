package Resident_Daemon;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class BasicFileUtils {
    private static boolean isValidFile(Path path) {
        //        Check if file exists
        if (!Files.exists(path)){
            return false;
        }

//        We don't want to copy a directory, just a file
        if (!Files.isRegularFile(path)) {
            return false;
        }

        return true;
    }

    public static boolean copyFile(Path src, Path dest) {
        if (src == null || dest == null) {
            return false;
        }

        if (!isValidFile(src)) {
            return false;
        }

//            https://stackoverflow.com/questions/4545937/java-splitting-the-filename-into-a-base-and-extension

        String fileName = src.getFileName().toString();
        String regex = "\\.(?=[^\\.]+$)";
        String withoutExt = fileName.split(regex)[0];
        String ext = fileName.split(regex)[1];

        String srcDir = src.getParent().toString();

        String destStr = null;

        if (dest.equals(src.getParent()) || src.equals(dest)) {

            destStr = srcDir + "/" + withoutExt + "-1." + ext;

            dest = Paths.get(destStr);
        }

//        We want to know what will be the name of the new file
        if (Files.isDirectory(dest)) {

//            If they contain the same directory
            destStr = dest.toString();

            if (destStr.toCharArray()[destStr.length() - 1] != '/') {
                destStr += "/";
            }
            destStr += fileName;
            dest = Paths.get(destStr);
        }

        try {
            Files.copy(src, dest, REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean bytes2file(byte[] src, Path dest) {

        if (dest == null || src == null || src.length == 0) {
            return false;
        }

        if (!Files.isRegularFile(dest)) {
            return false;
        }

        try {
            Files.write(dest, src);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static byte[] getBytesFromFile(Path src) {

        if (src == null || !isValidFile(src)) {
            return null;
        }

        try {
            return Files.readAllBytes(src);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
