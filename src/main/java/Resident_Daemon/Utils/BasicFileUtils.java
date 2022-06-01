package Resident_Daemon.Utils;

import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.SyncRecord;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.IntStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Base64.getEncoder;

public class BasicFileUtils {

    public static String filePathMasterSyncFile = "sync_files_evidence.data";

    public static boolean isValidFile(String path) {
        return BasicFileUtils.isValidFile(Paths.get(path));
    }

    public static boolean isValidFile(Path path) {
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

    private static byte[] codeFile(String fileRelPath, long timestamp, byte[] fileContent){

        String SfileContent = new String(fileContent, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder(fileRelPath + "!" + timestamp + "!" + SfileContent);

        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }



/////////////

    private static long GetTimeStamp(Path file) {
        long timestamp;

        try {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            timestamp = attrs.lastModifiedTime().toMillis();

            //            System.out.println(attrs.lastModifiedTime().toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return timestamp;
    }

    public static byte[] GetBytesToSend(String fileRelPath){

        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath().toString();

        Path filePath = Paths.get(folderPath, fileRelPath);

//        if(!isValidFile(filePath)) throw new InvalidPathException(filePath.toString(), "Wrong path");
        byte[] fileContent = null;
        if(isValidFile(filePath)) {
            fileContent = file2bytes(filePath);
        } else {
            throw new InvalidPathException(filePath.toString(), "Wrong path");
        }

        return codeFile(fileRelPath, GetTimeStamp(filePath), fileContent);

    }

    private static String getFilePath(String data){
        return data.substring(0, data.indexOf("!"));
    }
    private static String getTimeStamp(String data){
        return data.substring(data.indexOf("!") + 1, data.lastIndexOf("!") - 1);
    }
    private static String getContent(String data){
        return data.substring(data.lastIndexOf("!") + 1);
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

    public static FileData GetFileData(byte[] dataReceived){
        String receivedData = new String(dataReceived, StandardCharsets.UTF_8);

        String fileRelPath = getFilePath(receivedData);
        long timeStamp = Long.parseLong(getTimeStamp(receivedData));
        String fileContent = getContent(receivedData);

        FileData fileData = new FileData(fileRelPath, timeStamp, fileContent);

        return fileData;

    }

    /** format: `${filePath} ${isSync} ${timestamp}\n`*/
    public static void writeRecordsToMasterFileOverwrite(List<SyncRecord> records) throws IOException {

        var sb = new StringBuilder();
        sb.append(records.size() + "\n");
        for (var el : records) {
            sb.append(el.getFileRelPath() + " ");
            sb.append(el.getSynced() + " ");
            sb.append(el.getLastModifiedTimeStamp() + "\n");
        }

        Files.write(Paths.get(BasicFileUtils.GetMasterFilePath()), sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    /** format: `${filePath} ${isSync} ${timestamp}\n`*/
    public static void writeRecordToMasterFileAppend(SyncRecord record) throws IOException {

        var list = BasicFileUtils.readRecordsFromMasterFile();
        boolean found = false;
        for(var sr : list) {
            if (sr.equals(record)) {
                sr.setSynced(record.getSynced());
                sr.setLastModifiedTimeStamp(record.getLastModifiedTimeStamp());
                found = true;
                break;
            }
        }

        if (!found) {
            list.add(record);
        }

        var sb = new StringBuilder();
        sb.append(list.size() + "\n");
        for (var el : list) {
            sb.append(el.getFileRelPath() + " ");
            sb.append(el.getSynced() + " ");
            sb.append(el.getLastModifiedTimeStamp() + "\n");
        }

        Files.write(Paths.get(BasicFileUtils.GetMasterFilePath()), sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static List<SyncRecord> readRecordsFromBytes(byte[] bytes) {

        Input.confScanner(bytes);
        return Input.nextListOfRecords();
    }

    public static List<SyncRecord> readRecordsFromString(String str) {

        return readRecordsFromBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    public static List<SyncRecord> readRecordsFromMasterFile() {

        try {
            Input.confScanner(BasicFileUtils.GetMasterFilePath());
            return Input.nextListOfRecords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetMasterFilePath(){
        String relMFPath = BasicFileUtils.filePathMasterSyncFile;
        String absoluteFolderPath = Singleton.getSingletonObject().getFolderToSyncPath().toString();

        String absoluteMFPath = Paths.get(absoluteFolderPath, relMFPath).toString();

        return absoluteMFPath;
    }

    public static String GetAbsolutePath_FromRelative(String relPath){

        String absoluteFolderPath = Singleton.getSingletonObject().getFolderToSyncPath().toString();
        return Paths.get(absoluteFolderPath, relPath).toString();
    }

    public static void SaveRecordsToMasterFile() throws IOException {
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        List<SyncRecord> list = new ArrayList<>();
        for(var file : GetTextFiles.getTextFiles(folderPath).entrySet()){
            SyncRecord syncRecord = new SyncRecord(file.getKey().toString(), true);
            System.out.println(file.getKey());
            list.add(syncRecord);
        }
        BasicFileUtils.writeRecordsToMasterFileOverwrite(list);

    }

}
