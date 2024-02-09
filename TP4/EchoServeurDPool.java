import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.net.Socket;

public class EchoServeurDPool{
    Scanner clavier;
    String ip;
    int port;
    Executor executor;

    public static void main(String[] args) throws IOException{
        if (args.length == 1){
            try {
                EchoServeurDPool serveurDPool = new EchoServeurDPool(Integer.parseInt(args[0]));
                serveurDPool.serveur();
            } catch (Exception e) {
                System.out.println("Error Sythaxe : java EchoServeurDPool [-t nb] port");
            }
        }
        if (args.length == 3){
            if (args[0].compareTo("-t") == 0){
                try {
                    EchoServeurDPool serveurDPool = new EchoServeurDPool(Integer.parseInt(args[2]));
                    serveurDPool.serveur(Integer.parseInt(args[1]));
                } catch (Exception e) {
                    System.out.println("Error Sythaxe : java EchoServeurDPool [-t nb] port");
                }
            }
        }
        else{
            System.out.println("Error Sythaxe : java EchoServeurDPool [-t nb] port");
        }
    }

    public EchoServeurDPool(int port){
        this.clavier = new Scanner(System.in);
        this.ip = "localhost";
        this.port = port;
    }

    public void serveur() throws IOException{
        ServerSocket serveur = new ServerSocket(this.port, 10, InetAddress.getByName(ip));
        System.out.println("Server up\n-------------------\n");

        while (true) {
            System.out.println("Waiting for client ...");
            Socket client = serveur.accept();
            ClientTCPHandler handler = new ClientTCPHandler(client);
            this.executor = Executors.newCachedThreadPool();
            executor.execute(handler);
        }
    }

    public void serveur(int nbThreads) throws IOException{
        ServerSocket serveur = new ServerSocket(this.port, 10, InetAddress.getByName(ip));
        System.out.println("Server up\n-------------------\n");

        while (true) {
            System.out.println("Waiting for client ...");
            Socket client = serveur.accept();
            ClientTCPHandler handler = new ClientTCPHandler(client);
            this.executor = Executors.newFixedThreadPool(nbThreads);
            executor.execute(handler);
        }
    }

    public static void echoServer(String line){
        System.out.println(line);
    }
}