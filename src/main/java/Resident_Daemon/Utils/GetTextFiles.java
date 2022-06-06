package Resident_Daemon.Utils;

import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class GetTextFiles
{//Basic Files Utils file2bytes
    private static Path initialPath;

    private static Map<Path, byte[]> RecursiveCall(Path src) {
        Map<Path, byte[]> mapData = new HashMap<>();


        var dir = src.toFile();

        File[] files = dir.listFiles();

        if (files != null && files.length > 0)
        {
            for (File file : files)
            {
                // Check if the file is a directory
                if (file.isDirectory())
                {
                    var mapData2 = RecursiveCall(Path.of(file.getAbsolutePath()));
                    for (var entry : mapData2.entrySet())
                    {
                        mapData.put(entry.getKey(), entry.getValue());
                    }
                }
                else
                {
                    Path fileFullPath = Path.of(file.getAbsolutePath());
                    if(isTextPlainFile(fileFullPath))
                    {
                        Path base = initialPath;

                        var fileRelPath = base.relativize(fileFullPath);

                        mapData.put(fileRelPath, BasicFileUtils.file2bytes(Path.of(file.getAbsolutePath())));
                    }
                }
            }
        }
        return mapData;
    }

    public static Map<Path, byte[]> getTextFiles(Path path)
    {
        initialPath = path;

        Map<Path, byte[]> mapData = RecursiveCall(path);
        return mapData;
    }

    public static boolean isTextPlainFile(Path filePath) {

        try {
            String mimeType = Files.probeContentType(filePath);
            System.out.println(mimeType);
            return mimeType.equals("text/plain");
        } catch (IOException e) {
            return false;
        }


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
}
