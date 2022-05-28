package Resident_Daemon;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    public static boolean isDirectory(String sPath) {
        Path path = FileSystems.getDefault().getPath(sPath);

        return Files.isDirectory(path);
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

    public static byte[] file2bytes(Path src) {

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

    public static void writeListOfObjectsToFileInOverwriteMode(List<Object> objs, String filePath) throws IOException {

        if (objs.size() == 0)
            return;

        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeInt(objs.size());
        for (Object obj: objs)
            oos.writeObject(obj);

        oos.flush();
        oos.close();
    }


    public static List<Object> readListOfObjectsFromFile(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);

        List<Object> objs = new LinkedList<>();
        int numOfRecords = ois.readInt();

        for (int i = 0; i < numOfRecords; i++)
            objs.add(ois.readObject());

        ois.close();
        return objs;
    }

}
