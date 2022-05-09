package Version_Control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Comparator {
    private static List<Pair<String, String>> originalFiles;
    private static Map<String,List<String>> otherFiles;

    public Comparator(List<Pair<String, String>> originalFiles, Map<String,List<String>> otherFiles){
        this.originalFiles = originalFiles;
        this.otherFiles = otherFiles;
    }

    public static void fileContent(String ceva) throws IOException {
        BufferedReader fd = new BufferedReader(new StringReader(ceva));
        String line = "";
        while ((line = fd.readLine())!=null){
            System.out.println(line);
        }
        fd.close();
    }

    public static Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>> fileDifferences(String a, String b) throws IOException {
        List<Pair<Integer, String>> addedLines = new ArrayList<>();
        List<Pair<Integer, String>> removedLines = new ArrayList<>();

        Map<String, Integer> liniiFisier1 = new ConcurrentHashMap<>();
        Map<String, Integer> liniiFisier2 = new ConcurrentHashMap<>();

        BufferedReader fd1 = new BufferedReader(new StringReader(a));
        BufferedReader fd2 = new BufferedReader(new StringReader(b));

        String lineFile1 = "", lineFile2 = "";
        int contor = 0;
        while ((lineFile1 = fd1.readLine()) != null){
            liniiFisier1.put(lineFile1, contor);
            contor++;
        }
        contor = 0;
        while ((lineFile2 = fd2.readLine()) != null){
            liniiFisier2.put(lineFile2, contor);
            contor++;
        }

        for (String temp : liniiFisier1.keySet()){
            if (liniiFisier2.containsKey(temp)){
                liniiFisier1.remove(temp);
                liniiFisier2.remove(temp);
            }
        }

        for (Map.Entry<String, Integer> i : liniiFisier1.entrySet()){
            removedLines.add(new Pair<>(i.getValue(), i.getKey()));
        }
        removedLines.sort(new java.util.Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for (Map.Entry<String, Integer> i : liniiFisier2.entrySet()){
            addedLines.add(new Pair<>(i.getValue(), i.getKey()));
        }
        addedLines.sort(new java.util.Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return new Pair<>(addedLines, removedLines);
    }
    //La map-ul de conflicte am pus doar numele fisierului si o lista de intregi ce reprezinta lista numerelor liniilor la care au loc conflicte
    public static Pair< Map<String, Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>>>, Map<String, List<Integer>>> compare() throws IOException {
        Map<String, Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>>> mapNumeAddedRemoved = new HashMap<>();
        Map<String, List<Integer>> mapConflicte = new HashMap<>();

        for (Pair<String, String> i : originalFiles){
            Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>> temp;
            List<Pair<Integer, String>> tempAdded = new ArrayList<>();
            List<Pair<Integer, String>> tempRemoved = new ArrayList<>();
            for(String j : otherFiles.get(i.getKey())){
                Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>> diferente = fileDifferences(i.getValue(), j);
                diferente.getKey().stream().filter(e -> !tempAdded.contains(new Pair<>(e.getKey(), e.getValue()))).forEach(a -> tempAdded.add(a));
                diferente.getValue().stream().filter(e -> !tempAdded.contains(new Pair<>(e.getKey(), e.getValue()))).forEach(a -> tempRemoved.add(a));
                /*
                tempAdded.addAll(diferente.getKey());
                tempRemoved.addAll(diferente.getValue());
                */
            }
            tempAdded.sort(new java.util.Comparator<Pair<Integer, String>>() {
                @Override
                public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            tempRemoved.sort(new java.util.Comparator<Pair<Integer, String>>() {
                @Override
                public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            temp = new Pair<>(tempAdded, tempRemoved);
            mapNumeAddedRemoved.put(i.getKey(), temp);
        }

        return new Pair<>(mapNumeAddedRemoved, mapConflicte);
    }

    public static void main(String[] args) throws IOException {
        //Merge
        /*System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getKey().toString());
        System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getValue().toString());*/

        ArrayList<Pair<String, String>> temp2 = new ArrayList<>();
        temp2.add(new Pair<String, String>("primul", "Mama\nare\nmere"));

        ArrayList<String> temp = new ArrayList<>();
        temp.add("Mama\nare\nmere\nsi\npere");
        temp.add("Tata\nare\nmere\nsi\npere");
        Map<String, List<String>> temp1 = new HashMap<>();
        temp1.put("primul", temp);

        Comparator a = new Comparator(temp2, temp1);

        System.out.println(compare().getKey().toString());
        System.out.println(compare().getValue().toString());
    }
}

