package Version_Control;

import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Version_Control_Component {
    private List<Pair<String, String>> originalFiles;
    private Map<String,List<String>> otherFiles;
    private String versionFileData;
    ComparatorP2PFiles comparatorul;
    VersionFile fisierVersiuni;
    Version_Control_Component(){
        versionFileData = """
{"files":{"primul":{"2022/05/15 01:21":{"added_content":{"0":"Tata","3":"si","4":"pere"},"deleted_content":{"0":"Mama"}}}}}
    }
    """;
        fisierVersiuni = new VersionFile(versionFileData);
    }
    public void setOriginalFiles(List<Pair<String, String>> originalFiles) {
        this.originalFiles = originalFiles;
    }

    public void setOtherFiles(Map<String, List<String>> otherFiles) {
        this.otherFiles = otherFiles;
    }
    public void compare() throws IOException {
        comparatorul = new ComparatorP2PFiles(originalFiles, otherFiles);
       // System.out.println(comparatorul.compare().getKey());
        fisierVersiuni.setVersionFileData(comparatorul.compare().getKey());
        System.out.println(fisierVersiuni.getVersionFileData());
    }

    public static void main(String[] args) throws IOException {
        //Merge
        /*System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getKey().toString());
        System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getValue().toString());*/

        Version_Control_Component version = new Version_Control_Component();
        ArrayList<Pair<String, String>> temp2 = new ArrayList<>();
        temp2.add(new Pair<String, String>("primul", "Mama\nare\nmere"));

        ArrayList<String> temp = new ArrayList<>();
        temp.add("Mama\nare\nmere\nsi\npere");
        temp.add("Tata\nare\nmere\nsi\npere");
        Map<String, List<String>> temp1 = new HashMap<>();
        temp1.put("primul", temp);
        version.setOriginalFiles(temp2);
        version.setOtherFiles(temp1);
        version.compare();
    }
}

