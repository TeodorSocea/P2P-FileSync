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
    public void setVersionFileData(Map<String,Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>>> map)
    {
        JSONObject files = new JSONObject(VersionFileData);
        JSONObject fileList = new JSONObject(files.getJSONObject("files"));
        for(Map.Entry<String,Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>>> entry : map.entrySet()){
            JSONObject obj = fileList.optJSONObject(entry.getKey());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime time = LocalDateTime.now();
            String timestamp = time.format(dtf);
            JSONObject details = new JSONObject();
            JSONObject addedLines = new JSONObject();
            JSONObject removedLines = new JSONObject();
            for(Pair<Integer,String> added : entry.getValue().getKey()){
                addedLines.put(String.valueOf(added.getKey()),added.getValue());
            }
            for(Pair<Integer,String> removed : entry.getValue().getValue()){
                removedLines.put(String.valueOf(removed.getKey()),removed.getValue());
            }
            details.put("added_content",addedLines);
            details.put("deleted_content",removedLines);
            if(obj==null){
                obj = new JSONObject();
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

