package Test;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TestServeurTCP{
    static Scanner clavier = new Scanner(System.in);
    static String ip = "localhost";
    static int port = 1234;
    static int nbClients = 0;
    public static void main(String[] args) throws IOException{
        serveur();
    }

    public static void serveur() throws IOException{
        ServerSocket serveur = new ServerSocket(port, 10, InetAddress.getByName(ip));
        System.out.println("Server up\n-------------------\n");

        while (true) {
            System.out.println("Waiting for client ...");
            Socket client = serveur.accept();
            TestServeurTCP.incrementNbClient();
            System.out.println("New client connected ! n°" + nbClients);
            Thread serverAction = new Thread(){
                public void run(){
                    try {
                        serverAction(client);
                    } catch(Exception e) {
                        System.out.println("Error : probleme with server action");
                    }
                }
            };
            serverAction.start();
        }
    }

    public static void serverAction(Socket client) throws IOException{
        int nbClient = nbClients;

        InputStream input = client.getInputStream();
        OutputStream output = client.getOutputStream();

        String initMsgString = "Client n°" + nbClient;

        byte[] initMsg = initMsgString.getBytes();
        output.write(initMsg);

        byte[] buffer = new byte[4096];
        int bytes = 0;

        while ((bytes = input.read(buffer)) != -1) {
            String message = ">" + new String(buffer, 0, bytes);
            TestServeurTCP.echoServer("Message from client n°" + nbClient + " " + message);
            byte[] newBuffer = message.getBytes();
            output.write(newBuffer);
        }
    }

    public static void incrementNbClient(){
        nbClients++;
    }

    public static int getNbClient(){
        return nbClients;
    }

    public static void echoServer(String line){
        System.out.println(line);
    }
}