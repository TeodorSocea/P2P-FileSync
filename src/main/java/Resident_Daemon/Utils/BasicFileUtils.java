package Resident_Daemon.Utils;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.SyncRecord;
import Resident_Daemon.Core.UserData;
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
    public static final String VERSION_FILE_DATA_NAME = "versionfile.version";
    public static final String MASTER_FILE_DELIM = "!";

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

    public static long GetFileTimestamp(int swarmID, String fileRelPath) {
        Path file = Paths.get(BasicFileUtils.GetAbsolutePath_FromRelative(swarmID, fileRelPath));
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


    public static byte[] GetBytesToSend(String fileRelPath, int swarmID){

        String swarmFolderPath = String.valueOf(GetSwarmFolderPath(swarmID));

        Path filePath = Paths.get(swarmFolderPath, fileRelPath);

        long lastModifiedTimeStamp = GetFileTimestamp(swarmID, fileRelPath);

//        if(!isValidFile(filePath)) throw new InvalidPathException(filePath.toString(), "Wrong path");
        byte[] fileContent = null;
        if(isValidFile(filePath)) {
            fileContent = file2bytes(filePath);
        } else {
            throw new InvalidPathException(filePath.toString(), "Wrong path");
        }

        return codeFile(fileRelPath, lastModifiedTimeStamp, fileContent);

    }

    private static int findNthOccur(String str,
                     char ch, int N)
    {
        int occur = 0;

        // Loop to find the Nth
        // occurrence of the character
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == ch)
            {
                occur += 1;
            }
            if (occur == N)
                return i;
        }
        return -1;
    }

    private static String getFilePath(String data){
        return data.substring(0, data.indexOf("!"));
    }
    private static String getTimeStamp(String data){
        return data.substring(data.indexOf("!") + 1, findNthOccur(data, '!', 2) - 1);
    }
    private static String getContent(String data){
        return data.substring(findNthOccur(data, '!', 2) + 1);
    }

    public static void WriteFileToFolder(int swarmID, String fileRelPath, String data) throws IOException {

        String swarmFolderPath = String.valueOf(GetSwarmFolderPath(swarmID));

        Path filePath = Paths.get(swarmFolderPath, fileRelPath);

        if(filePath.toString().contains("\\")){
            filePath.getParent().toFile().mkdirs();
        }

        try {
            Files.writeString(filePath, data);
        } catch (IOException e) {
            throw e;
        }

    }

//    public static void WriteFileToFolder(byte[] dataReceived) throws IOException {
//
//        String folderPath = String.valueOf(Singleton.getSingletonObject().getFolderToSyncPath());
//
//        String receivedData = new String(dataReceived);
//
//        String receivedPath = getFilePath(receivedData);
//        System.out.println("Fisier: " + receivedPath);
//
//        Path filePath = Paths.get(folderPath, receivedPath);
//
//        if(filePath.toString().contains("\\")){
//            filePath.getParent().toFile().mkdirs();
//        }
//
//        String whatToWrite = getContent(receivedData);
//
//        try {
//            Files.writeString(filePath, whatToWrite);
//        } catch (IOException e) {
//            throw e;
//        }
//
//    }

    public static FileData GetFileData(byte[] dataReceived){
        String receivedData = new String(dataReceived, StandardCharsets.UTF_8);

        String fileRelPath = getFilePath(receivedData);
        long timeStamp = Long.parseLong(getTimeStamp(receivedData));
        String fileContent = getContent(receivedData);

        FileData fileData = new FileData(fileRelPath, timeStamp, fileContent);

        return fileData;

    }

    /** format: `${filePath} ${isSync} ${timestamp}\n`*/
    public static void writeRecordsToMasterFileOverwrite(List<SyncRecord> records, Path masterFilePath) throws IOException {

        String d = BasicFileUtils.MASTER_FILE_DELIM;

        var sb = new StringBuilder();
        sb.append(records.size() + d);
        for (var el : records) {
            sb.append(el.getFileRelPath() + d);
            sb.append(el.getSynced() + d);
            sb.append(el.getLastModifiedTimeStamp() + d);
        }

        Files.write(masterFilePath, sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    /** format: `${filePath} ${isSync} ${timestamp}\n`*/
    public static void writeRecordToMasterFileAppend(SyncRecord record, int swarmID) throws IOException {

        var list = BasicFileUtils.readRecordsFromMasterFile(swarmID);
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

        Files.write(Paths.get(BasicFileUtils.GetMasterFilePath(swarmID)), sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static List<SyncRecord> readRecordsFromBytes(byte[] bytes) {

        Input.confScanner(bytes);
        return Input.nextListOfRecords();
    }

    public static List<SyncRecord> readRecordsFromString(String str) {

        return readRecordsFromBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    public static List<SyncRecord> readRecordsFromMasterFile(int swarmID) {

        try {
            Input.confScanner(BasicFileUtils.GetMasterFilePath(swarmID));
            return Input.nextListOfRecords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetMasterFilePath(int swarmID){
        String MFName = BasicFileUtils.filePathMasterSyncFile;

        Path swarmFolderPath = GetSwarmFolderPath(swarmID);

        String absoluteMFPath = Paths.get(String.valueOf(swarmFolderPath), MFName).toString();

        return absoluteMFPath;
    }

    public static String GetAbsolutePath_FromRelative(int swarmID, String relPath){
        String swarmFolderPath = GetSwarmFolderPath(swarmID).toString();

        return Paths.get(swarmFolderPath, relPath).toString();
    }

    public static void SaveRecordsToMasterFile(int swarmID) throws IOException {
        Path swarmFolderPath = GetSwarmFolderPath(swarmID);

        List<SyncRecord> list = new ArrayList<>();
        for(var file : GetTextFiles.getTextFiles(swarmFolderPath).entrySet()){
            long lastModifiedTimeStamp = GetFileTimestamp(swarmID, String.valueOf(file.getKey()));
            SyncRecord syncRecord = new SyncRecord(file.getKey().toString(), true, lastModifiedTimeStamp);
            list.add(syncRecord);
        }

        BasicFileUtils.writeRecordsToMasterFileOverwrite(list, Paths.get(BasicFileUtils.GetMasterFilePath(swarmID)));

    }

    public static void DeleteMaterFile(int swarmID) {
        try {
            Files.deleteIfExists(Path.of(BasicFileUtils.GetMasterFilePath(swarmID)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path GetSwarmFolderPath(String swarmName) {
        Path swarmFolderPath = Paths.get(String.valueOf(Singleton.getSingletonObject().getFolderToSyncPath()), swarmName);

        return swarmFolderPath;
    }

    public static Path GetSwarmFolderPath(int swarmID) {
        String swarmName = GetSwarmName(swarmID);
        Path swarmFolderPath = Paths.get(String.valueOf(Singleton.getSingletonObject().getFolderToSyncPath()), swarmName);

        return swarmFolderPath;
    }

    public static void CreateSwarmFolder(String swarmName) {
        Path swarmFolderPath = GetSwarmFolderPath(swarmName);
        new File(String.valueOf(swarmFolderPath)).mkdirs();

    }

    public static String GetSwarmName(int swarmID) {
        NetworkingComponent nc = Singleton.getSingletonObject().getNetworkingComponent();
        for(var entry : nc.getSwarms().entrySet()) {
            if(entry.getKey().equals(swarmID)){
                return entry.getValue().getSwarmName();
            }
        }

        return null;
    }

    public static void CreateLocalMasterFile(int swarmID) {

        UserData userData = Singleton.getSingletonObject().getUserData();
        Path swarmFolderPath = GetSwarmFolderPath(swarmID);

        for(var file : GetTextFiles.getTextFiles(swarmFolderPath).entrySet()){
            long lastModifiedTimeStamp = GetFileTimestamp(swarmID, String.valueOf(file.getKey()));
            userData.getLocalMasterFile().add(new SyncRecord (file.getKey().toString(), true, lastModifiedTimeStamp));
        }
    }

    public static Path GetVersionFilePath(int swarmID) {
        Path swarmFolderPath = GetSwarmFolderPath(swarmID);
        Path versionFilePath = Paths.get(String.valueOf(swarmFolderPath), VERSION_FILE_DATA_NAME);

        return versionFilePath;
    }

    public static String GetIfExistsVersionFileData(int swarmID){

        Path versionFile = GetVersionFilePath(swarmID);

        byte[] versionFileBytes = BasicFileUtils.file2bytes(versionFile);

        if(versionFileBytes != null){
            String versionFileData = new String(versionFileBytes, StandardCharsets.UTF_8);
            return versionFileData;
        }

        return null;
    }

    public static void WriteVersionFile(int swarmID, String versionFileData){

        Path versionFilePath = GetVersionFilePath(swarmID);

        try {
            Files.writeString(versionFilePath, versionFileData);
        } catch (IOException e) {
            System.out.println("Error at writing Version File!");
        }
    }



}
