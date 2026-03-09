package problema;

import java.util.HashMap;
import java.util.Map;

public class ProblemaHashMap {

    private static Map<String, Integer> contadorAcessos = new HashMap<>();

    public static void main(String[] args) {

        Runnable tarefa = () -> {
            for (int i = 0; i < 1000; i++) {
                Integer valor = contadorAcessos.get("user");

                if (valor == null) {
                    contadorAcessos.put("user", 1);
                } else {
                    contadorAcessos.put("user", valor + 1);
                }
            }
        };

        Thread t1 = new Thread(tarefa);
        Thread t2 = new Thread(tarefa);

        t1.start();
        t2.start();
    }
}
