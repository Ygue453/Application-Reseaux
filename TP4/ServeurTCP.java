import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP{
    String ip;
    int port;

    public static void main(String[] args) throws IOException{
        ServeurTCP serveurTCP = new ServeurTCP();
        serveurTCP.serveur();
    }

    public ServeurTCP(){
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
            (new Thread(handler)).start();
        }
    }

    public static void echoServer(String line){
        System.out.println(line);
    }
}