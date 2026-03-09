package solucao;

import java.util.concurrent.ConcurrentHashMap;

public class SolucaoHashMap {

    private static ConcurrentHashMap<String, Integer> contadorAcessos = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        Runnable tarefa = () -> {
            for (int i = 0; i < 1000; i++) {
                contadorAcessos.merge("user", 1, Integer::sum);
            }
        };

        Thread t1 = new Thread(tarefa);
        Thread t2 = new Thread(tarefa);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Total de acessos: " + contadorAcessos.get("user"));
    }
}
