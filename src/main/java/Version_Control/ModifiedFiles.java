package Version_Control;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class ModifiedFiles {
    public List<Pair<String,String>> buildModifiedFiles(List<Pair<String,String>> originalFiles, Map<String,Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>>> modifications) throws IOException {
        List<Pair<String,String>> modifiedFiles = new ArrayList<>();
        for(Pair<String,String> file : originalFiles){
            List<String> newModifiedFile = new ArrayList<>();
            Pair<List<Pair<Integer,String>>,List<Pair<Integer,String>>> value = modifications.get(file.getKey());
            List<Pair<Integer,String>> added = value.getKey();
            List<Pair<Integer,String>> removed = value.getValue();
            BufferedReader bf = new BufferedReader(new StringReader(file.getValue()));
            String lineFile = "";
            int counter = 0;
            int positionAdded = 0;
            int positionRemoved = 0;
            while((lineFile=bf.readLine())!=null){
                if(positionRemoved<removed.size()){
                    if(counter==removed.get(positionRemoved).getKey()){
                        if(positionAdded<added.size() && counter==added.get(positionAdded).getKey()){
                            newModifiedFile.add(added.get(positionAdded).getValue());
                            positionAdded++;
                        }
                        positionRemoved++;
                    }
                    else{
                        if(positionAdded<added.size()){
                            if(counter==added.get(positionAdded).getKey()){
                                do{
                                    newModifiedFile.add(added.get(positionAdded).getValue());
                                    positionAdded++;
                                    counter++;
                                }while(positionAdded<added.size() && added.get(positionAdded).getKey()==added.get(positionAdded-1).getKey()+1);
                            }
                        }
                        newModifiedFile.add(lineFile);
                    }
                }
                else
                {
                    if(positionAdded<added.size()){
                        if(counter==added.get(positionAdded).getKey()){
                            do{
                                newModifiedFile.add(added.get(positionAdded).getValue());
                                positionAdded++;
                                counter++;
                            }while(positionAdded<added.size() && added.get(positionAdded).getKey()==added.get(positionAdded-1).getKey()+1);
                        }
                    }
                    newModifiedFile.add(lineFile);
                }
                counter++;
            }
            for(int i=positionAdded;i<added.size();i++){
                newModifiedFile.add(added.get(i).getValue());
            }
            String modifiedFileAsString = "";
            for (String s : newModifiedFile) {
                modifiedFileAsString = modifiedFileAsString.concat(s + "\n");
            }
            modifiedFiles.add(new Pair<>(file.getKey(),modifiedFileAsString));
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