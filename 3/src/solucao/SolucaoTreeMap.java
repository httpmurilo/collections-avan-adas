import java.util.Map;
import java.util.TreeMap;

public class SolucaoTreeMap {

    public static void main(String[] args) {

        Map<Integer, String> transacoes = new TreeMap<>();

        transacoes.put(300, "Compra Mercado");
        transacoes.put(100, "Café");
        transacoes.put(500, "Eletrônico");

        for (Map.Entry<Integer, String> t : transacoes.entrySet()) {
            System.out.println(t.getKey() + " - " + t.getValue());
        }
    }
}