package Resident_Daemon.Utils;

import Resident_Daemon.Core.Singleton;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

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

    private static byte[] codeFile(String fileRelPath, byte[] fileContent){

        String SfileContent = new String(fileContent, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder(fileRelPath + "!" + SfileContent);

        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }



/////////////

    public static byte[] GetBytesToSend(String fileRelPath){

        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath().toString();

        Path filePath = Paths.get(folderPath, fileRelPath);

        if(!isValidFile(filePath)) throw new InvalidPathException(filePath.toString(), "Wrong path");
        byte[] fileContent = file2bytes(filePath);

        return codeFile(fileRelPath, fileContent);

    }

    private static String getFilePath(String data){
        return data.substring(0, data.indexOf("!"));
    }
    private static String getContent(String data){
        return data.substring(data.indexOf("!") + 1);
    }

    public static void WriteFileToFolder(String fileRelPath, String data) throws IOException {

        String folderPath = String.valueOf(Singleton.getSingletonObject().getFolderToSyncPath());

        Path filePath = Paths.get(folderPath, fileRelPath);

        if(filePath.toString().contains("\\")){
            filePath.getParent().toFile().mkdirs();
        }

        try {
            Files.writeString(filePath, data);
        } catch (IOException e) {
            throw e;
        }

    }

    public static void WriteFileToFolder(byte[] dataReceived) throws IOException {

        String folderPath = String.valueOf(Singleton.getSingletonObject().getFolderToSyncPath());

        String receivedData = new String(dataReceived);

        String receivedPath = getFilePath(receivedData);
        System.out.println("Fisier: " + receivedPath);

        Path filePath = Paths.get(folderPath, receivedPath);

        if(filePath.toString().contains("\\")){
            filePath.getParent().toFile().mkdirs();
        }

        String whatToWrite = getContent(receivedData);

        try {
            Files.writeString(filePath, whatToWrite);
        } catch (IOException e) {
            throw e;
        }

    }

    public static Pair<String, String> GetFileData(byte[] dataReceived){
        String receivedData = new String(dataReceived, StandardCharsets.UTF_8);

        Pair<String, String> fileData = new Pair<>(getFilePath(receivedData), getContent(receivedData));

        return fileData;

    }

}
