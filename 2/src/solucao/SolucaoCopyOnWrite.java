package solucao;

import java.util.concurrent.ConcurrentHashMap;

public class SolucaoCopyOnWrite {

    private static CopyOnWriteArrayList<String> usuarios = new CopyOnWriteArrayList<>();

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
