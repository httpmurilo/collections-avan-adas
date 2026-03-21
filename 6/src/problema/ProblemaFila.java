import java.util.ArrayList;
import java.util.List;

public class ProblemaFila {

    private static List<String> fila = new ArrayList<>();

    public static void main(String[] args) {

        Runnable produtor = () -> {
            for (int i = 0; i < 5; i++) {
                fila.add("Tarefa " + i);
                System.out.println("Produziu: Tarefa " + i);
            }
        };

        Runnable consumidor = () -> {
            for (int i = 0; i < 5; i++) {
                if (!fila.isEmpty()) {
                    String tarefa = fila.remove(0);
                    System.out.println("Consumiu: " + tarefa);
                }
            }
        };

        new Thread(produtor).start();
        new Thread(consumidor).start();
    }
}