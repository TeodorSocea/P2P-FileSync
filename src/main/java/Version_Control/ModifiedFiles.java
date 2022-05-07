package Version_Control;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class ModifiedFiles {
    public List<Pair<String,String>> buildModifiedFiles(ArrayList<Pair<String,String>> originalFiles, Map<String,Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>>> modifications) throws IOException {
        List<Pair<String,String>> modifiedFiles = new ArrayList<>();
        Comparator<Pair<Integer,String>> comparator = new Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                if(o1.getKey()>o2.getKey()){
                    return -1;
                } else if(o1.getKey().equals(o2.getKey())){
                    return 0;
                } else {
                    return 1;
                }
            }
        };
        for(Pair<String,String> file : originalFiles){
            if(modifications.containsKey(file.getKey())){
                String newModifiedFile = "";
                Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>> value = modifications.get(file.getKey());
                List<Pair<Integer,String>> added = value.getKey();
                List<Pair<Integer,String>> removed = value.getValue();
                if(!added.isEmpty())
                    Collections.sort(added,comparator);
                if(!removed.isEmpty())
                    Collections.sort(removed,comparator);
                BufferedReader bf = new BufferedReader(new StringReader(file.getValue()));
                String lineFile = "";
                int counter = 1;
                if(removed.isEmpty()){
                    while((lineFile = bf.readLine())!=null){
                        newModifiedFile = newModifiedFile.concat(lineFile);
                    }
                    if(!added.isEmpty()){
                        for(int i=0;i<value.getKey().size();i++)
                            newModifiedFile = newModifiedFile.concat(added.get(i).getValue());
                    }
                    modifiedFiles.add(new Pair<>(file.getKey(),newModifiedFile));
                }
                else{
                    while ((lineFile = bf.readLine())!= null){
                        if(counter!=removed.get(0).getKey()){
                            newModifiedFile = newModifiedFile.concat(lineFile);
                            counter++;
                        }
                        else{
                            if(!added.isEmpty()){
                                for(int i=0;i<value.getKey().size();i++)
                                    newModifiedFile = newModifiedFile.concat(added.get(i).getValue());
                            }
                            modifiedFiles.add(new Pair<>(file.getKey(),newModifiedFile));
                            break;
                        }
                    }
                }

            }
        }
        return modifiedFiles;
    }
}
