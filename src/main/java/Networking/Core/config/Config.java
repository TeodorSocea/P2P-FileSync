package Networking.Core.config;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Config {

    // Static variable reference of single_instance
    // of type Singleton
    private static Config single_instance = null;

    // Declaring a variable of type int
    public static int UDP_PORT;


    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private Config() {

    }
    //Getter
    public int UDPPORT() {

        return 10101;
    }

    public static void setUdpPort(int udpPort) {
        UDP_PORT = udpPort;
    }

    public static void init() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("ConfigFile.json"));
            JSONObject jsonObject = (JSONObject) obj;

            /*
            Iterator iterator = subjects.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
            */
            setUdpPort(12);
            System.out.println(UDP_PORT + " hello");
        } catch (Exception e) {
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
