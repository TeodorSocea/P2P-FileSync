package Resident_Daemon.PackageOS;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalAPI extends DetectOS {
    public void CreateNewFile(String fileName) {
        try {
            String home = System.getProperty("user.home"); //functia asta creaza un fisier nou la adresa de acasa a userului
            Path path = Paths.get(home, fileName);
            Files.createDirectories(path.getParent());
            try {
                Files.createFile(path);
            } catch (FileAlreadyExistsException e) {
                System.err.println("File already exists: " + e.getMessage()); //se verifica daca exista deja fisierul
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void WindowsCommands(String fileName) {
        CreateNewFile(fileName);
    }
    public void MacOSCommands(String fileName) {
        CreateNewFile(fileName);
    }
    public void UnixCommands(String fileName) {
        CreateNewFile(fileName);
    }
}

