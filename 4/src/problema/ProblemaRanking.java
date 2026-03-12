import java.util.TreeMap;
import java.util.Map;

public class ProblemaRanking {

    private static TreeMap<Integer, String> ranking = new TreeMap<>();

    public static void main(String[] args) {

        Runnable atualizar = () -> {
            for (int i = 0; i < 100; i++) {
                ranking.put(i, Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(atualizar);
        Thread t2 = new Thread(atualizar);

        t1.start();
        t2.start();

        for (Map.Entry<Integer, String> r : ranking.entrySet()) {
            System.out.println(r);
        }
    }
}