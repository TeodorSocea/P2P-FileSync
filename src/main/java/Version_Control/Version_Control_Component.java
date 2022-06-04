package Version_Control;

import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Version_Control_Component {
    private List<FileP2P> originalFiles;
    private List<FileP2P> otherFiles;
    private List<Pair<String,Long>> localMasterFile;
    private List<Pair<String,Long>> otherMasterFile;
    private String versionFileData;
    ComparatorP2PFiles comparatorul;
    ComparatorMasterFile comparatorulMasterFile;
    VersionFile fisierVersiuni;
    Rollback rollback;
    String rollbackFile;
    String rollbackFileName;
    public Version_Control_Component(){
        initVersionFileData();
        fisierVersiuni = new VersionFile(versionFileData);
    }
    public Version_Control_Component(String versionFileData){
        this.versionFileData = versionFileData;
        fisierVersiuni = new VersionFile(versionFileData);
    }
    public void setOriginalFiles(List<FileP2P> originalFiles) {
        this.originalFiles = originalFiles;
    }

    public void setOtherFiles(List<FileP2P> otherFiles) {
        this.otherFiles = otherFiles;
    }

    public List<FileP2P> getOtherFiles() {
        return otherFiles;
    }

    public void compare() throws IOException {
        comparatorul = new ComparatorP2PFiles(originalFiles, otherFiles);
       // System.out.println(comparatorul.compare().getKey());
        fisierVersiuni.setVersionFileData(comparatorul.compare());
//        System.out.println("Fisiere originale inainte de modificare: " );
//        System.out.println(this.originalFiles.toString());
//        System.out.println("Fisiere originale dupa modificare: ");
        ModifiedFiles modify = new ModifiedFiles();
        this.originalFiles = modify.buildModifiedFiles(originalFiles, otherFiles, comparatorul.compare());
//        System.out.println(this.originalFiles.toString());
//        System.out.println(fisierVersiuni.getVersionFileData());
    }
    public void compareMasterFile(){
        comparatorulMasterFile = new ComparatorMasterFile(localMasterFile, otherMasterFile);
        this.localMasterFile = comparatorulMasterFile.listaFinala();
    }
    public void setVersionFileData(String versionFileData) {
        this.versionFileData = versionFileData;
    }
    public void rollbackFile(long timeStamp){
        rollback = new Rollback(this.versionFileData);
        rollback.rollbackTo(this.rollbackFile, this.rollbackFileName, timeStamp);
        this.rollbackFile = rollback.getRollbackedFile();
        setFisierVersiuni(rollback.getVersionFileData());
    }
    public void initVersionFileData(){
        versionFileData = """
                            {
                              "files": {
                             
                              }
                            }
                """;
    }

    public List<FileP2P> getOriginalFiles() {
        return originalFiles;
    }

    public String getVersionFileData() {
        return fisierVersiuni.getVersionFileData();
    }

    public void setFisierVersiuni(String fisierVersiuni) {
        this.versionFileData = fisierVersiuni;
        this.fisierVersiuni.setVersionFileData(fisierVersiuni);
    }

    public List<Pair<String, Long>> getLocalMasterFile() {
        return localMasterFile;
    }

    public void setLocalMasterFile(List<Pair<String, Long>> localMasterFile) {
        this.localMasterFile = localMasterFile;
    }

    public List<Pair<String, Long>> getOtherMasterFile() {
        return otherMasterFile;
    }

    public void setOtherMasterFile(List<Pair<String, Long>> otherMasterFile) {
        this.otherMasterFile = otherMasterFile;
    }

    public String getRollbackFile() {
        return rollbackFile;
    }

    public void setRollbackFile(String rollbackFile) {
        this.rollbackFile = rollbackFile;
    }

    public String getRollbackFileName() {
        return rollbackFileName;
    }

    public void setRollbackFileName(String rollbackFileName) {
        this.rollbackFileName = rollbackFileName;
    }

    public static void main(String[] args) throws IOException {
        //Merge
        /*System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getKey().toString());
        System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getValue().toString());*/

      /*  Version_Control_Component version = new Version_Control_Component("""
                {"files":{"primul":{"2022/05/15 16:01":{"added_content":{"0":"Tata","3":"si","4":"pere"},"deleted_content":{"0":"Mama"}}}}}""");*/ //constructor version file data
      /*  Version_Control_Component version = new Version_Control_Component();

        List<Pair<String, Long>> primaLista = new ArrayList<>();

        Pair<String, Long> b = new Pair<String, Long>("alDoilea", 6L);
        Pair<String, Long> c = new Pair<String, Long>("alTreilea", 7L);
        Pair<String, Long> d = new Pair<String, Long>("alPatrulea", 8L);
        //primaLista.add(a);
        primaLista.add(b);
        primaLista.add(c);
        primaLista.add(d);

        List<Pair<String, Long>> aDouaLista = new ArrayList<>();
        Pair<String, Long> g = new Pair<String, Long>("primul", 7L);
        Pair<String, Long> j = new Pair<String, Long>("alPatrulea", 6L);
        Pair<String, Long> k = new Pair<String, Long>("alPatrulea", 7L);
        Pair<String, Long> l = new Pair<String, Long>("alCincilea", 8L);
        aDouaLista.add(g);
        aDouaLista.add(j);
        aDouaLista.add(k);
        aDouaLista.add(l);
        version.setLocalMasterFile(primaLista);
        version.setOtherMasterFile(aDouaLista);
        version.compareMasterFile();
        System.out.println(version.getLocalMasterFile());
        //scriere*/
        String dataFisier="ana are mere\n" +
                "maria are mere\n" +
                "ben are mere\n";
        String numeFisier = "nume_fisier_sincronizat";
        String VersionFile = "{\n" +
                "  \"files\": {\n" +
                "    \"nume_fisier_sincronizat\": {\n" +
                "    \"1653837570\" : {\n" +
                "      \"added_content\" : { 3:\"ben are mere\"},\n" +
                "      \"deleted_content\" : { 3:\"ana nu are mere\"}\n" +
                "    },\n" +
                "    \"1651728809\" :  {\n" +
                "      \"added_content\" : { 2:\"maria are mere\"},\n" +
                "      \"deleted_content\" : { 2:\"maria nu are mere\", 4:\"ana are multe mere\"}\n" +
                "  	},\n" +
                "    },\n" +
                "    \"alt_fisier_sincronizat\": {\n" +
                "    \"1653837570\" : {\n" +
                "      \"added_content\" : { 3:\"ben are mere\"},\n" +
                "      \"deleted_content\" : { 3:\"ana nu are mere\"}\n" +
                "    },\n" +
                "    },\n" +
                "    },\n" +
                "}";
        long rollbackTimestamp = 1651728809;
        Version_Control_Component a = new Version_Control_Component(VersionFile);
        a.setRollbackFile(dataFisier);
        a.setRollbackFileName(numeFisier);
        a.rollbackFile(rollbackTimestamp);
        System.out.println(a.getRollbackFile());
        System.out.println(a.getVersionFileData());
        VersionFileParser parser = new VersionFileParser(a.getVersionFileData());
        ArrayList<String> files = parser.getFiles();
        ArrayList<String> timestamps = parser.getTimestampsOfFile(files.get(1));
        System.out.println(parser.getChangesOfFile(files.get(1), timestamps.get(0)));
        //System.out.println(parser.getFiles());
    }
}

