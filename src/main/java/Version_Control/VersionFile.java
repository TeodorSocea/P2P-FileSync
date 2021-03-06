package Version_Control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import org.json.JSONObject;

public class VersionFile {
    String VersionFileData;
    public VersionFile(String VersionFileData){
        this.VersionFileData = VersionFileData;
//        System.out.println("fisierul de versiuni este:");
//        System.out.println(getVersionFileData());
    }
    public void setVersionFileData(Map<String,Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>>> map)
    {
        JSONObject files = new JSONObject(VersionFileData);
        JSONObject fileList = new JSONObject();
        fileList = files.getJSONObject("files");
        for(Map.Entry<String,Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>>> entry : map.entrySet()){
            if(!(entry.getValue().getKey().isEmpty() && entry.getValue().getValue().isEmpty())){
                JSONObject obj = fileList.optJSONObject(entry.getKey());
                //Old version of timestamp:
                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                //LocalDateTime time = LocalDateTime.now();
                //String timestamp = time.format(dtf);
                Date date = new Date();
                long time = date.getTime()/1000L;
                String timestamp = Long.toString(time);
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
        }
        JSONObject finalForm = new JSONObject();
        finalForm.put("files",fileList);
        VersionFileData = finalForm.toString();
    }

    public String getVersionFileData() {
        return VersionFileData;
    }

    public void setVersionFileData(String versionFileData) {
        VersionFileData = versionFileData;
    }
}

