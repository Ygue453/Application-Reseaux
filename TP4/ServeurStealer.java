import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.net.Socket;

public class ServeurStealer{
    Scanner clavier;
    String ip;
    int port;
    Executor executor;

    public static void main(String[] args) throws IOException{
        ServeurStealer serveurDPool = new ServeurStealer();
        serveurDPool.serveur();
    }

    public ServeurStealer(){
        this.clavier = new Scanner(System.in);
        this.ip = "localhost";
        this.port = 1234;
    }

    public void serveur() throws IOException{
        ServerSocket serveur = new ServerSocket(this.port, 10, InetAddress.getByName(ip));
        System.out.println("Server up\n-------------------\n");

        while (true) {
            System.out.println("Waiting for client ...");
            Socket client = serveur.accept();
            ClientTCPHandler handler = new ClientTCPHandler(client);
            this.executor = Executors.newWorkStealingPool();
            executor.execute(handler);
        }
    }

    public static void echoServer(String line){
        System.out.println(line);
    }
}