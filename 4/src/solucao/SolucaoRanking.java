import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;

public class SolucaoRanking {

    private static ConcurrentSkipListMap<Integer, String> ranking = new ConcurrentSkipListMap<>();

    public static void main(String[] args) throws InterruptedException {

        Runnable atualizar = () -> {
            for (int i = 0; i < 100; i++) {
                ranking.put(i, Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(atualizar);
        Thread t2 = new Thread(atualizar);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        for (Map.Entry<Integer, String> r : ranking.entrySet()) {
            System.out.println(r);
        }
    }
}