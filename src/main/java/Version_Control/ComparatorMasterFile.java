package Version_Control;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ComparatorMasterFile {
    private List<Pair<String,Long>> original;
    private List<Pair<String,Long>> other;

    public ComparatorMasterFile(List<Pair<String, Long>> original, List<Pair<String, Long>> other) {
        this.original = original;
        this.other = other;
    }

    public List<Pair<String, Long>> listaFinala(){
        List<Pair<String, Long>> finalList = new ArrayList<>();

        for(Pair<String,Long> i : original){
            Pair<String, Long> temp = new Pair<>(i.getKey(), i.getValue());
            //daca pt fisierul din original exista fisier in other
            if (other.stream().anyMatch(e->e.getKey().equals(i.getKey()))){
                for (Pair<String,Long> j : other){
                    if (j.getKey().equals(temp.getKey()) && j.getValue() > temp.getValue()){
                        temp = j;
                    }
                }
                finalList.add(temp);
            }
            //daca pt fisierul din original nu exista fisier in other
            else{
                finalList.add(i);
            }
        }
        //acum iau fisierele care sunt in other si nu sunt in original
        for (Pair<String,Long> i : other){
            Pair<String, Long> temp = new Pair<>(i.getKey(), i.getValue());
            //verifica daca nu exista in original
            if (original.stream().noneMatch(e->e.getKey().equals(i.getKey()))){
                for (Pair<String,Long> j : other){
                    if (!j.equals(i) && j.getKey().equals(temp.getKey()) && j.getValue() > temp.getValue()){
                        temp = j;
                    }
                }
                finalList.add(temp);
            }
        }
        return finalList;
    }

    public List<Pair<String, Long>> getOriginal() {
        return original;
    }
    public void setOriginal(List<Pair<String, Long>> original) {
        this.original = original;
    }
    public List<Pair<String, Long>> getOther() {
        return other;
    }
    public void setOther(List<Pair<String, Long>> other) {
        this.other = other;
    }

    public static void main (String[] args){
        List<Pair<String, Long>> primaLista = new ArrayList<>();
        Pair<String, Long> a = new Pair<String, Long>("primul", 5L);
        Pair<String, Long> b = new Pair<String, Long>("alDoilea", 6L);
        Pair<String, Long> c = new Pair<String, Long>("alTreilea", 7L);
        Pair<String, Long> d = new Pair<String, Long>("alPatrulea", 8L);
        primaLista.add(a);
        primaLista.add(b);
        primaLista.add(c);
        primaLista.add(d);

        List<Pair<String, Long>> aDouaLista = new ArrayList<>();
        Pair<String, Long> e = new Pair<String, Long>("primul", 5L);
        Pair<String, Long> f = new Pair<String, Long>("primul", 6L);
        Pair<String, Long> g = new Pair<String, Long>("primul", 7L);
        Pair<String, Long> h = new Pair<String, Long>("alDoilea", 5L);
        Pair<String, Long> i = new Pair<String, Long>("alDoilea", 4L);
        Pair<String, Long> j = new Pair<String, Long>("alPatrulea", 6L);
        Pair<String, Long> k = new Pair<String, Long>("alPatrulea", 7L);
        Pair<String, Long> l = new Pair<String, Long>("alCincilea", 8L);
        aDouaLista.add(e);
        aDouaLista.add(f);
        aDouaLista.add(g);
        aDouaLista.add(h);
        aDouaLista.add(i);
        aDouaLista.add(j);
        aDouaLista.add(k);
        aDouaLista.add(l);

        ComparatorMasterFile temp = new ComparatorMasterFile(primaLista, aDouaLista);

        List<Pair<String,Long>> output = temp.listaFinala();
        for(Pair<String,Long> z : output){
            System.out.println(z.getKey()+" "+z.getValue());
        }
    }
}
