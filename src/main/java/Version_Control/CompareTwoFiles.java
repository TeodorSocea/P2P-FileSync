package Version_Control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompareTwoFiles extends Version_Control_Component {
    public static void fileDifferences(File a, File b) throws IOException {
        Map<String, Integer> liniiFisier1 = new ConcurrentHashMap<>();
        Map<String, Integer> liniiFisier2 = new ConcurrentHashMap<>();

        BufferedReader fd1 = new BufferedReader(new FileReader(a));
        BufferedReader fd2 = new BufferedReader(new FileReader(b));

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
        System.out.println("Liniile urmatoare au fost sterse: ");
        for (Map.Entry<String, Integer> i : liniiFisier1.entrySet()){
            System.out.println(i.getValue() + ". " + i.getKey());
        }
        System.out.println("");
        System.out.println("Liniile urmatoare au fost adaugate: ");
        for (Map.Entry<String, Integer> i : liniiFisier2.entrySet()){
            System.out.println(i.getValue() + ". " + i.getKey());
        }
    }
}
