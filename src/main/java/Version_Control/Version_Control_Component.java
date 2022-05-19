package Version_Control;

import org.w3c.dom.ls.LSOutput;


public class Version_Control_Component {
    public static void main(String[] args) {
        Conflicts conflict;
        FileP2P f = new FileP2P("C:\\Users\\Patricia\\source\\repos\\P2P-FileSync\\P2P-FileSync\\src\\main\\java\\Version_Control\\test1");
        System.out.println(f.data);
        System.out.println(f.binaryCheck());
        System.out.println(f.timestamp);
    }
}
