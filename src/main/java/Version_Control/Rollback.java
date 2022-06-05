package Version_Control;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.*;

public class Rollback {
    String VersionFileData;
    String rollbackedFile;
    public Rollback(String VersionFileData){
        this.VersionFileData = VersionFileData;
    }

    public String getVersionFileData() {
        return VersionFileData;
    }

    public void setVersionFileData(String versionFileData) {
        VersionFileData = versionFileData;
    }

    public String getRollbackedFile() {
        return rollbackedFile;
    }


    public void rollbackTo(String dataFisier, String numeFisier, long rollbackTimestamp){
        JSONObject json = new JSONObject(this.VersionFileData);
        HashMap<String,JSONObject> timestamps = new HashMap<>();
        HashMap<String, String> toBeDeleted = new HashMap<>();
        HashMap<String, String> toBeAdded = new HashMap<>();
        JSONObject nou;
        JSONObject file = json.getJSONObject("files").getJSONObject("" + numeFisier + "");
        List<String> myList;

        //timestamps va lua toate timestamp-urile relevante pana si inclusiv cel de la reollbackTimestamp si le va salva intr-un hashmap
        Iterator<?> keys = file.keys();
        while(keys.hasNext()){
            String key = (String) keys.next();
            if(Long.parseLong(key)>=rollbackTimestamp) {
                timestamps.put(key, file.getJSONObject(key));
            }
        }
        //sortam hashmap
        //crescator
        TreeSet<String> tsc = new TreeSet<>(timestamps.keySet());
        //descrescator
        TreeSet<String> ts = (TreeSet<String>)tsc.descendingSet();

        int iteratieContor = 0;


        HashMap<String, String> toBeDeletedFirst = new HashMap<>();

        for (String key : ts) {///swap la toata zona de deleted_content si added_content + swap la myList = List.copyOf(addTo/deleteFrom)
            String value = String.valueOf(timestamps.get(key));

            //iteram strict prin added content pentru a salva intr-un map linia si continutul pentru a-l pune mai usor in json mai tarziu?

            JSONObject added = new JSONObject(value).getJSONObject("added_content");


            Iterator<String> addItr = added.keys();
            while(addItr.hasNext()) {
                String name = addItr.next();
                toBeAdded.put(name, added.getString(name));
                if(iteratieContor == 0) {
                    toBeDeletedFirst.put(name, added.getString(name));
                }
            }
            // aplicam liniile modificare si dupa modificam si fisierul sa fie la curent cu adaugarile facute
            myList = List.copyOf(deleteFrom(added,dataFisier));
            dataFisier = String.join("\n",myList);




            //iteram strict prin deleted content pentru a salva intr-un map linia si continutul pentru a-l pune mai usor in json mai tarziu?
            JSONObject deleted = new JSONObject(value).getJSONObject("deleted_content");
            Iterator<String> delItr = deleted.keys();
            while(delItr.hasNext()) {
                String name = delItr.next();
                toBeDeleted.put(name, deleted.getString(name));
            }
            // stergem liniile si dupa modificam si fisierul sa fie la curent cu modificarile facut
            myList = List.copyOf(addTo(deleted,dataFisier));
            dataFisier = String.join("\n",myList);


            iteratieContor++;

        }
        nou = createNewJSONObject(toBeDeleted,toBeDeletedFirst); /// switch aici
        json.getJSONObject("files").getJSONObject("" + numeFisier + "").put("" + System.currentTimeMillis() / 1000L + "",nou);
        file.put("" + System.currentTimeMillis() / 1000L + "",nou);
        this.rollbackedFile = dataFisier;
        setVersionFileData(json.toString());
    }


    private HashMap<String,JSONObject> timestamp(JSONObject file, long rollbackTimestamp){
        //obtineam json-urile in ordine diferita ochiometric, probabil nu e o problema dar o sa o las aici pentru accessibility
        HashMap<String,JSONObject> timestamps1 = new HashMap<>();
        Iterator<?> keys = file.keys();
        while(keys.hasNext()){
            String key = (String) keys.next();
            if(Integer.parseInt(key)<=Integer.parseInt(String.valueOf(rollbackTimestamp)))
                timestamps1.put(key, file.getJSONObject(key));
        }
        return timestamps1;
    }

    private List<String> deleteFrom(JSONObject deleted, String dataFisier){
        //sparg fisierul in lista pentru a-l parsa mai usor si a da remove direct liniei din cheie
        int removed = 0;
        List<String> myList = new ArrayList<String>(Arrays.asList(dataFisier.split("\n")));
        for (Object key: deleted.keySet()){
            String number = key.toString();
            int i=Integer.parseInt(number);
            myList.remove(i-removed);
            removed++;
        }
        return myList;
    }

    private List<String> addTo(JSONObject added, String dataFisier){
        //sparg fisierul in lista pentru a-l parsa mai usor si a pune continutul direct pe linia cheii
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(dataFisier.split("\n")));
        for (Object key: added.keySet()){
            String number = key.toString();
            String text = (String) added.get("" + number + "");
            int i=Integer.parseInt(number);
            if(i>myList.size()){
            do{
                myList.add("");
            }while(i>myList.size());}
            if(i <= myList.size()){
            myList.add(i, text);}
        }
        return myList;
    }

    private JSONObject createNewJSONObject(HashMap<String,String> added, HashMap<String,String> deleted){
        JSONObject array = new JSONObject();

        JSONObject add = new JSONObject(added);
        array.put("added_content",add);

        JSONObject del = new JSONObject(deleted);
        array.put("deleted_content",del);

        return array;
    }
    public static void main(String[] args) {
        String dataFisier="ana are mere\n" +
                "maria are mere\n" +
                "ben are mere\n";
        String numeFisier = "nume_fisier_sincronizat";
        String VersionFile = "{\n" +
                "  \"files\": {\n" +
                "    \"nume_fisier_sincronizat\": {\n" +
                "    \"1653837570\" : {\n" +
                "      \"added_content\" : { 3:\"ben are mere\"},\n" +
                "      \"deleted_content\" : { 3:\"ana NU MAI are mere\"}\n" +
                "    },\n" +
                "    \"1651728809\" :  {\n" +
                "      \"added_content\" : { 2:\"maria are mere\"},\n" +
                "      \"deleted_content\" : { 2:\"maria nu are mere\", 4:\"ana are multe mere\"}\n" +
                "  	},\n" +
                "    },\n" +
                "    },\n" +
                "}";
        long rollbackTimestamp = 1651728809;
        Rollback rollback = new Rollback(VersionFile);

        rollback.rollbackTo(dataFisier,numeFisier,rollbackTimestamp);
        /*
        //primeste tot json-ul
        ArrayList<String> files = new ArrayList<>(rollback.getFiles(new JSONObject(VersionFile)));

        //primeste direct json-ul fisierului care se doreste cautat, implicit si partea cu .getJSONObject("files")
        //le veti ordonate?
        JSONObject json = new JSONObject(VersionFile);
        json = json.getJSONObject("files").getJSONObject("nume_fisier_sincronizat");
        ArrayList<String> timestamps = new ArrayList<>(rollback.getTimestampOfFile(json));

        //primeste direct timestamp-ul, implicit si fisierul
        JSONObject keys;
        keys = json.getJSONObject("1651901609");
        rollback.getKeysOfTimestamp(keys);*/
    }
}
