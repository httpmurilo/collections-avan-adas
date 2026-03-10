package problema;

import java.util.ArrayList;
import java.util.List;

public class ProblemaArrayList {

    private static List<String> usuarios = new ArrayList<>();

    public static void main(String[] args) {

        usuarios.add("Ana");
        usuarios.add("Carlos");
        usuarios.add("Pedro");

        Runnable leitura = () -> {
            for (String user : usuarios) {
                System.out.println("Lendo: " + user);
            }
        };

        Runnable escrita = () -> {
            usuarios.add("NovoUsuario");
        };

        Thread t1 = new Thread(leitura);
        Thread t2 = new Thread(escrita);

        t1.start();
        t2.start();
    }
}