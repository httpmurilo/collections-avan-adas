import java.util.HashMap;
import java.util.Map;

public class ProblemaHashMap {

    public static void main(String[] args) {

        Map<Integer, String> transacoes = new HashMap<>();

        transacoes.put(300, "Compra Mercado");
        transacoes.put(100, "Café");
        transacoes.put(500, "Eletrônico");

        for (Map.Entry<Integer, String> t : transacoes.entrySet()) {
            System.out.println(t.getKey() + " - " + t.getValue());
        }
    }
}