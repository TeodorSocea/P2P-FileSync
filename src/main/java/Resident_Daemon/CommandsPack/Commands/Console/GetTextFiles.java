package Resident_Daemon.CommandsPack.Commands.Console;

import Resident_Daemon.FileAux.BasicFileUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetTextFiles
{//Basic Files Utils file2bytes
    public static ArrayList<Map<File, byte[]>> getTextFiles(Path src)
    {
        ArrayList<Map<File, byte[]>> list = new ArrayList<Map<File, byte[]>>();

        var map = new HashMap<File, byte[]>();

        var dir = src.toFile();

        File[] files = dir.listFiles();

        if (files != null && files.length > 0)
        {
            for (File file : files)
            {
                // Check if the file is a directory
                if (file.isDirectory())
                {
                    var list2 = getTextFiles(Path.of(file.getAbsolutePath()));
                    for (var map1 : list2)
                    {
                        if( map1 != null) list.add(map1);
                    }
                }
                else
                {
                    if(textFileFilter.accept(dir, file.getName()))
                    {
                        var fullPath = file.getAbsolutePath();
                        map.put(new File(fullPath), BasicFileUtils.file2bytes(Path.of(fullPath)));
                    }
                }
            }
        }
        if ( map != null )list.add(map);
        return  list;
    }

    private static final FilenameFilter textFileFilter = new FilenameFilter()
    {
        public boolean accept(File dir, String name)
        {
            String lowercaseName = name.toLowerCase();
            if (lowercaseName.endsWith(".txt")) {
                return true;
            } else
            {
                return false;
            }
        }
    };

//    public static void main(String[] args)
//    {
//        Input.confScanner();
//        // Reading data using readLine
//        String name = null;
//        try
//        {
//            name = ChooseFolder.GetFolderPath();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        Path p = Path.of(name);
//        var g = getTextFiles(p);
//        for ( int i = 0 ; i < g.size() ; ++i )
//        {
//            var map = g.get(i).entrySet();
//            if ( map == null ) System.out.println("a a");
//            for ( var entry : map )
//            {
//                System.out.println(entry.getKey());
//            }
//        }
//    }
}
