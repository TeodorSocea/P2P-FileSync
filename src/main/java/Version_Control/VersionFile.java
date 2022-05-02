package Version_Control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import org.json.JSONObject;

public class VersionFile {
    String VersionFileData;
    public VersionFile(String VersionFileData){
        this.VersionFileData = VersionFileData;
    }
    public void setVersionFileData(Map<String,Pair<List<AddedRemoved>,Pair<String,String>>> map)
    {
        JSONObject files = new JSONObject(VersionFileData);
        JSONObject fileList = new JSONObject(files.getJSONObject("files"));
        for(Map.Entry<String,Pair<List<AddedRemoved>,Pair<String,String>>> entry : map.entrySet()){
            JSONObject obj = fileList.optJSONObject(entry.getKey());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime time = LocalDateTime.now();
            String timestamp = time.format(dtf);
            JSONObject details = new JSONObject();
            details.put("user",entry.getValue().getValue().getKey());
            details.put("hash",entry.getValue().getValue().getValue());
            JSONObject addedLines = new JSONObject();
            JSONObject removedLines = new JSONObject();
            for(AddedRemoved item : entry.getValue().getKey()){
                addedLines.put(String.valueOf(item.addedLineNumber),item.addedLineContent);
                removedLines.put(String.valueOf(item.removedLineNumber),item.removedLineContent);
            }
            details.put("added_content",addedLines);
            details.put("deleted_content",removedLines);
            if(obj==null){
                obj.put(timestamp,details);
                fileList.put(entry.getKey(),obj);
            }
            else
            {
                obj.put(timestamp,details);
                fileList.remove(entry.getKey());
                fileList.put(entry.getKey(),obj);
            }
        }
        JSONObject finalForm = new JSONObject();
        finalForm.put("files",fileList);
        VersionFileData = finalForm.toString();
    }

    public String getVersionFileData() {
        return VersionFileData;
    }
}


