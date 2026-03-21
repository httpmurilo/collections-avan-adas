import java.util.Set;
import java.util.TreeSet;

public class ProblemaSet {

    private static Set<Integer> requisicoes = new TreeSet<>();

    public static void main(String[] args) {

        Runnable tarefa = () -> {
            for (int i = 0; i < 1000; i++) {
                requisicoes.add(i);
            }
        };

        Thread t1 = new Thread(tarefa);
        Thread t2 = new Thread(tarefa);

        t1.start();
        t2.start();

        for (Integer r : requisicoes) {
            System.out.println(r);
        }
    }
}