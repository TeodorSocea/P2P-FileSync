package Networking.Core.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Config {


    private static Config single_instance = null;


    public static int UDP_PORT;



    private Config() {

    }
    //Getter
    public int UDPPORT() {

        return UDP_PORT;
    }

    public static void setUdpPort(int udpPort) {
        UDP_PORT = udpPort;
    }

    public static void init() {
        JSONParser parser = new JSONParser();
        try {
            Path path = FileSystems.getDefault().getPath("");
            String directoryName = path.toAbsolutePath().toString();
            String secondPartOfThePath="\\src\\main\\java\\Networking\\Core\\config\\ConfigFile.json";
            String finalPath =directoryName+secondPartOfThePath;
            
            Object obj = parser.parse(new FileReader(finalPath));
            org.json.simple.JSONObject jsonObject = (JSONObject)obj;
            String port = (String)jsonObject.get("port");


            int port1 = Integer.parseInt(port);

            UDP_PORT=port1;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Static method
    // Static method to create instance of Singleton class
    public static Config getInstance() {
        if (single_instance == null) {
            single_instance = new Config();
            init();
        }
        return single_instance;
    }


}
