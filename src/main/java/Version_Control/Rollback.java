package Version_Control;

import org.json.JSONObject;
import java.io.IOException;
import java.util.*;

public class Rollback {
    public static void main(String[] args) throws IOException {
        //just for the sake of the example i will do smth like this
        JSONObject da = new JSONObject();
        JSONObject nu = new JSONObject();
        JSONObject dada = new JSONObject();
        JSONObject dadada = new JSONObject();
        JSONObject ts1 = new JSONObject();
        JSONObject ts2 = new JSONObject();
        JSONObject ts3 = new JSONObject();
        JSONObject add1 = new JSONObject();
        JSONObject rmv1 = new JSONObject();
        JSONObject add2 = new JSONObject();
        JSONObject rmv2 = new JSONObject();
        JSONObject add3 = new JSONObject();
        JSONObject rmv3 = new JSONObject();
        da.put("files",nu);

        nu.put("nume_fisier_sincronizat",dada);
        dada.put("timestamp1", ts1);
        dada.put("timestamp2", ts2);
        ts1.put("user","marcel");
        ts1.put("hash","3easdas");
        ts1.put("added_content",add1);
        add1.put("1","ana are mere");
        add1.put("2","ana are multe mere");
        add1.put("5","maria are mere");
        ts1.put("deleted_content",rmv1);
        rmv1.put("3","ana nu are mere");

        ts2.put("user","pikachu");
        ts2.put("hash","dsadsadsa");
        ts2.put("added_content",add2);
        add2.put("10","ana are mere");
        ts2.put("deleted_content",rmv2);

        nu.put("alt_fisier_sincronizat",dadada);
        dadada.put("timestamp",ts3);
        ts3.put("user","marcel");
        ts3.put("hash","3easdas");
        ts3.put("added_content",add3);
        add3.put("1","ana are mere");
        add3.put("2","ana are multe mere");
        add3.put("5","maria are mere");
        ts3.put("deleted_content",rmv3);
        rmv3.put("3","ana nu are mere");

        //System.out.println(da);

        String dataFisier="ai\n" +
                "putea\n" +
                "crede\n" +
                "ca\n" +
                "functioneaza\n";
        List<String> myList = new ArrayList<String>(Arrays.asList(dataFisier.split("\n")));

        System.out.println("myList before " + myList);

        JSONObject jsonObject2 = (JSONObject) da.get("files");
        //System.out.println(jsonObject2);
        JSONObject jsonObject3 = (JSONObject) jsonObject2.get("nume_fisier_sincronizat");//il primesc in functie
        //System.out.println(jsonObject3);
        JSONObject jsonObject4 = (JSONObject) jsonObject3.get("timestamp1");//one rollback or specific rollback
        //System.out.println(jsonObject4);
        JSONObject jsonObject5 = (JSONObject) jsonObject4.get("added_content");
        //System.out.println(jsonObject5);
        JSONObject jsonObject6 = (JSONObject) jsonObject4.get("removed_content");
        System.out.println(jsonObject6);


        for (Object key: jsonObject5.keySet()){
            String number = key.toString();
            String text = (String) jsonObject5.get(number);
            int i=Integer.parseInt(number);
            myList.add(i, text);
        }
        System.out.println("myList after added" + myList);

        for (Object key: jsonObject5.keySet()){
            String number = key.toString();
            int i=Integer.parseInt(number);
            myList.remove(i);
        }
        System.out.println("myList after removed" + myList);
    }
}
