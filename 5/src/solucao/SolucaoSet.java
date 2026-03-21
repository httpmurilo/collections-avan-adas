import java.util.concurrent.ConcurrentSkipListSet;

public class SolucaoSet {

    private static ConcurrentSkipListSet<Integer> requisicoes = new ConcurrentSkipListSet<>();

    public static void main(String[] args) throws InterruptedException {

        Runnable tarefa = () -> {
            for (int i = 0; i < 1000; i++) {
                requisicoes.add(i);
            }
        };

        Thread t1 = new Thread(tarefa);
        Thread t2 = new Thread(tarefa);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        for (Integer r : requisicoes) {
            System.out.println(r);
        }
    }
}