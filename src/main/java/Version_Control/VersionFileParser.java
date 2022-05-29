package Version_Control;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VersionFileParser {
    JSONObject versionFile;
    JSONObject files;
    public VersionFileParser(String versionFile){
        this.versionFile = new JSONObject(versionFile);
    }
    public ArrayList<String> getFiles(){
        ArrayList<String> returned = new ArrayList<>();
        JSONObject files;
        files = this.versionFile.getJSONObject("files");
        Iterator<String> keys = files.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            returned.add(key);
            if(files.get(key) instanceof JSONArray) {
                JSONArray array = (JSONArray) files.get(key);
                JSONObject object = (JSONObject) array.get(0);
                Iterator<String> innerKeys = object.keys();
                while(innerKeys.hasNext()) {
                    String innerKey = innerKeys.next();
                    returned.add(innerKey);
                }
            }
        }
        return returned;
    }

    public ArrayList<String> getTimestampsOfFile(String fileName){
        JSONObject VersionFile = this.versionFile.getJSONObject("files").getJSONObject("" + fileName + "");
        ArrayList<String> returned = new ArrayList<>();
        Iterator<String> keys = VersionFile.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            returned.add(key);
            if(VersionFile.get(key) instanceof JSONArray) {
                JSONArray array = (JSONArray) VersionFile.get(key);
                JSONObject object = (JSONObject) array.get(0);
                Iterator<String> innerKeys = object.keys();
                while(innerKeys.hasNext()) {
                    String innerKey = innerKeys.next();
                    returned.add(innerKey);
                }
            }
        }
        return returned;
    }

    public HashMap<String, Map<String,String>>  getChangesOfFile(String fileName, String timestamp){
        JSONObject VersionFile = this.versionFile.getJSONObject("files").getJSONObject("" + fileName + "").getJSONObject("" + timestamp + "");
        HashMap<String, Map<String,String>> returned = new HashMap<>();
        HashMap<String,String> added = new HashMap<>(toMap(VersionFile.getJSONObject("added_content")));
        HashMap<String,String> removed = new HashMap<>(toMap(VersionFile.getJSONObject("deleted_content")));

        returned.put("added_content",added);
        returned.put("deleted_content",removed);
    return returned;
    }

    private HashMap<String, String> toMap(JSONObject VersionFile){
        HashMap<String,String> add = new HashMap<>();
        Iterator x = VersionFile.keys();
        JSONArray jsonArray = new JSONArray();
        while (x.hasNext()){
            String key = (String) x.next();
            //System.out.printf(key+": ");
            jsonArray.put(VersionFile.get(key));
            //System.out.printf((String) VersionFile.get(key));
            add.put(key,(String) VersionFile.get(key));
            //System.out.println();
        }
        return add;
    }

}
