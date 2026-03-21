import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SolucaoFila {

    private static BlockingQueue<String> fila = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        Runnable produtor = () -> {
            try {
                for (int i = 0; i < 5; i++) {
                    fila.put("Tarefa " + i);
                    System.out.println("Produziu: Tarefa " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumidor = () -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String tarefa = fila.take();
                    System.out.println("Consumiu: " + tarefa);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(produtor).start();
        new Thread(consumidor).start();
    }
}