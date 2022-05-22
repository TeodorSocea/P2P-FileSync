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
    private String versionFileData;
    ComparatorP2PFiles comparatorul;
    VersionFile fisierVersiuni;

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

    public void setVersionFileData(String versionFileData) {
        this.versionFileData = versionFileData;
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

    public static void main(String[] args) throws IOException {
        //Merge
        /*System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getKey().toString());
        System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getValue().toString());*/

      /*  Version_Control_Component version = new Version_Control_Component("""
                {"files":{"primul":{"2022/05/15 16:01":{"added_content":{"0":"Tata","3":"si","4":"pere"},"deleted_content":{"0":"Mama"}}}}}""");*/ //constructor version file data
        Version_Control_Component version = new Version_Control_Component();

        List<FileP2P> prima = new ArrayList<>();
        List<FileP2P> aDoua = new ArrayList<>();
        FileP2P a = new FileP2P();
        a.setData("Mama\nare\nmere");
        a.setFileName("primul");
        a.setTimestamp(1555);
        prima.add(a);

        FileP2P b = new FileP2P();
        b.setData("Mama\nare\nmere\nsi\npere");
        b.setFileName("primul");
        b.setTimestamp(1666);

        FileP2P c = new FileP2P();
        c.setData("Tata\nare\nmere\nsi\npere");
        c.setFileName("primul");
        c.setTimestamp(1777);

        aDoua.add(b);
        aDoua.add(c);
        version.setOriginalFiles(prima);
        version.setOtherFiles(aDoua);
      //  version.setOriginalFiles(temp2);
      //  version.setOtherFiles(temp1);
        version.compare();

        version.getOriginalFiles();

        //scriere
    }
}

