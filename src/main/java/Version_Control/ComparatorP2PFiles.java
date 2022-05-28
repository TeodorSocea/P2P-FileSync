package Version_Control;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ComparatorP2PFiles {
    private static List<FileP2P> originalFiles;
    private static List<FileP2P> otherFiles;

    public ComparatorP2PFiles(List<FileP2P> originalFiles, List<FileP2P> otherFiles){
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

    public static Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>> fileDifferences(FileP2P a, FileP2P b) throws IOException {
        List<Pair<Integer, String>> addedLines = new ArrayList<>();
        List<Pair<Integer, String>> removedLines = new ArrayList<>();

        Map<String, Integer> liniiFisier1 = new ConcurrentHashMap<>();
        Map<String, Integer> liniiFisier2 = new ConcurrentHashMap<>();

        BufferedReader fd1 = new BufferedReader(new StringReader(a.getData()));
        BufferedReader fd2 = new BufferedReader(new StringReader(b.getData()));

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
    //In map vom avea doar added si removed lines adica List<Pair<Integer, String>> reprezinta o lista in care vom adauga perechi de tipul
    // <5, Continutul liniei> - ce poate reprezenta fie added fie removed lines.
    //Map ul contine si un FileP2P pentru a sti la care fisier se face referire
    //Sper ca ajuta :)
    public static Map<String, Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>>> compare() throws IOException {
        Map<String, Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>>> mapNumeAddedRemoved = new HashMap<>();

        for (FileP2P i : originalFiles) {
            Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>> temp;
            List<Pair<Integer, String>> tempAdded = new ArrayList<>();
            List<Pair<Integer, String>> tempRemoved = new ArrayList<>();
            List<FileP2P> toBeSorted = new ArrayList<>();
            Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>> diferente = null;
            if (!otherFiles.isEmpty() && otherFiles.stream().anyMatch(e -> e.getFileName() != null && e.getFileName().equals(i.getFileName()))) {
                for (FileP2P j : otherFiles) {
                    if (j.getFileName().equals(i.getFileName())) {
                        toBeSorted.add(j);
                    }
                }
                toBeSorted.stream().sorted((o1, o2) -> {
                    if (o1.getTimestamp() > o2.getTimestamp())
                        return 1;
                    else if (o1.getTimestamp() < o2.getTimestamp())
                        return -1;
                    else
                        return 0;
                }).collect(Collectors.toList());
                diferente = fileDifferences(i, toBeSorted.get(toBeSorted.size() - 1));
                diferente.getKey().stream().filter(e -> !tempAdded.contains(new Pair<>(e.getKey(), e.getValue()))).forEach(a -> tempAdded.add(a));
                diferente.getValue().stream().filter(e -> !tempAdded.contains(new Pair<>(e.getKey(), e.getValue()))).forEach(a -> tempRemoved.add(a));

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
                mapNumeAddedRemoved.put(i.getFileName(), temp);
            } else {
                //diferente = fileDifferences(i, new FileP2P(i.getFileName(), "", System.currentTimeMillis() / 1000L));
            }
        }
        return mapNumeAddedRemoved;
    }

    public static void main(String[] args) throws IOException {
        //Merge
        /*System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getKey().toString());
        System.out.println(fileDifferences("Mama\nare\nmere", "Tata\nare\nmere\nsi\npere").getValue().toString());*/
        List<FileP2P> prima = new ArrayList<>();
        List<FileP2P> aDoua = new ArrayList<>();
        FileP2P a = new FileP2P();
        a.setData("Mama\nare\nmere");
        a.setFileName("primul");
        a.setTimestamp(1555);
        prima.add(a);

        FileP2P a1 = new FileP2P();
        //a1.setData("Mama\nare\nmere");
        //a1.setFileName("primul");
        //a1.setTimestamp(1555);
        prima.add(a1);

        FileP2P b = new FileP2P();
        b.setData("Mama\nare\nmere\nsi\npere");
        b.setFileName("primul");
        b.setTimestamp(1666);

        FileP2P c = new FileP2P();
        c.setData("Tata\nare\nmere\nsi\npere");
        c.setFileName("primul");
        c.setTimestamp(1777);

        FileP2P d = new FileP2P();

        //aDoua.add(d);
        aDoua.add(b);
        aDoua.add(c);

        ComparatorP2PFiles ceva = new ComparatorP2PFiles(prima, aDoua);
        System.out.println(ceva.compare().toString());
        /*ArrayList<Pair<String, String>> temp2 = new ArrayList<>();
        temp2.add(new Pair<String, String>("primul", "Mama\nare\nmere"));

        ArrayList<String> temp = new ArrayList<>();
        temp.add("Mama\nare\nmere\nsi\npere");
        temp.add("Tata\nare\nmere\nsi\npere");
        Map<String, List<String>> temp1 = new HashMap<>();
        temp1.put("primul", temp);

        ComparatorP2PFiles a = new ComparatorP2PFiles(temp2, temp1);*/
       /// Pair< Map<String, Pair<List<Pair<Integer, String>>, List<Pair<Integer, String>>>>, Map<String, List<Integer>>>
        //Map<String, Pair<List<Pair<Integer, String>>
        //System.out.println(compare().getKey().get("primul").getValue().get(0).getValue());
    }
}

