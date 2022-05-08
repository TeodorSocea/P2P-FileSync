package Version_Control;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class ModifiedFiles {
    public List<Pair<String,String>> buildModifiedFiles(List<Pair<String,String>> originalFiles, Map<String,Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>>> modifications) throws IOException {
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
                System.out.println(removed.get(removed.size()-1).getKey());
                if(removed.isEmpty()){
                    while((lineFile = bf.readLine())!=null){
                        newModifiedFile = newModifiedFile.concat(lineFile+"\n");
                    }
                    if(!added.isEmpty()){
                        for(int i=value.getKey().size()-1;i>=0;i--)
                            newModifiedFile = newModifiedFile.concat(added.get(i).getValue());
                    }
                    modifiedFiles.add(new Pair<>(file.getKey(),newModifiedFile));
                }
                else{
                    while ((lineFile = bf.readLine())!= null){
                        if(counter!=removed.get(removed.size()-1).getKey()){
                            newModifiedFile = newModifiedFile.concat(lineFile+"\n");
                            counter++;
                        }
                        else{
                            if(!added.isEmpty()){
                                for(int i=value.getKey().size()-1;i>=0;i--)
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
    /*
    * public static void main(String[] args) throws IOException {
        List<Pair<String,String>> originalFiles = new ArrayList<>();
        Map<String,Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>>> modifications = new HashMap<>();
        originalFiles.add(new Pair<>("proiect.txt","Acest proiect este\nBun\nCeva"));
        List<Pair<Integer,String>> added = new ArrayList<>();
        added.add(new Pair<>(2,"Interesant si\n"));
        added.add(new Pair<>(3,"Bun\n"));
        added.add(new Pair<>(4,"Altceva"));
        List<Pair<Integer,String>> removed = new ArrayList<>();
        removed.add(new Pair<>(2,"Bun\n"));
        removed.add(new Pair<>(3,"Ceva"));
        modifications.put("proiect.txt",new Pair<>(added,removed));
        ModifiedFiles mod = new ModifiedFiles();
        List<Pair<String,String>> modifiedFiles = mod.buildModifiedFiles(originalFiles,modifications);
        for(Pair<String,String> item : modifiedFiles){
            System.out.println(item.getValue());
        }
    }*/
}