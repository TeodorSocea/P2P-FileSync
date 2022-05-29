package Version_Control;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TempMain {
    public static void main(String args[]) throws IOException {
        int n = 1000;
        String[] names = new String[n];
        for(int i = 0; i < n; i++) {
            names[i] = "D:\\sth\\f" + i + ".txt";
        }
        createNFiles(names);
        List<FileP2P> files = new ArrayList<>();
        List<FileP2P> other = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            String data = readFile(names[i]);
            long timestamp = 29052002;
            FileP2P f = new FileP2P(names[i], data, timestamp);
            files.add(f);
            FileP2P f2 = f;
            if(i%3 == 0) {
                writeData(names[i], "modif1");
                data = readFile(names[i]);
                f2 = new FileP2P(names[i], data, timestamp);
            }
            else if(i%5 == 0) {
                writeData(names[i], "modif2");
                data = readFile(names[i]);
                f2 = new FileP2P(names[i], data, timestamp);
            }
            other.add(f2);
        }

        Version_Control_Component v = new Version_Control_Component();
        v.setOriginalFiles(files);
        v.setOtherFiles(other);

        long start1 = System.nanoTime();
        String result = analize(v);
        long end1 = System.nanoTime();
        double time1 = (double) (end1 - start1)/1_000_000_000;
        String s1 = "Citire pentru " + n + " fisiere = " + time1 + " secunde";

        String filename = "C:\\Users\\Patricia\\source\\repos\\p2p\\P2P-FileSync\\src\\main\\java\\Version_Control\\text";
        long start2 = System.nanoTime();
        writeData(filename, result);
        long end2 = System.nanoTime();
        double time2 = (double) (end2 - start2)/1_000_000_000;
        String s2 = "Scriere version file pentru " + n + " fisiere = " + time2 + " secunde";

        System.out.println(s1);
        System.out.println(s2);
        System.out.println("done");
    }

    public static String analize(Version_Control_Component v) throws IOException {
        v.compare();
        String ver = v.getVersionFileData();
        return ver;
    }

    public static void createNFiles(String[] names) {
        try
        {
            for(int j = 0; j < names.length; j++) {
                File file = new File(names[j]);
                file.createNewFile();
            }
        }
        catch (Exception e)
        {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static String readFile(String filename) {
        String data = "";
        try (FileInputStream file = new FileInputStream(new File(filename))) {
            int content;
            while((content = file.read()) != -1) {
                data += (char)content;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeData(String filename, String data) {
        try
        {
            FileWriter w = new FileWriter(filename);
            w.write(data);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
