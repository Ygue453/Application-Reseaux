package Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;
import java.net.Socket;

public class ServeurTCP{
    Scanner clavier;
    String ip;
    int port;
    //int nbClients;

    public static void main(String[] args) throws IOException{
        ServeurTCP serveurTCP = new ServeurTCP();
        serveurTCP.serveur();
    }

    public ServeurTCP(){
        this.clavier = new Scanner(System.in);
        this.ip = "localhost";
        this.port = 1234;
        //this.nbClients = 0;
    }

    public void serveur() throws IOException{
        ServerSocket serveur = new ServerSocket(this.port, 10, InetAddress.getByName(ip));
        System.out.println("Server up\n-------------------\n");

        while (true) {
            System.out.println("Waiting for client ...");
            Socket client = serveur.accept();
            //incrementNbClient();
            //System.out.println("New client connected ! n°" + nbClients);
            ClientTCPHandler handler = new ClientTCPHandler(client);
            (new Thread(handler)).start();
        }
    }

    /*public void incrementNbClient(){
        nbClients++;
    }

    public int getNbClient(){
        return nbClients;
    }*/

    public static void echoServer(String line){
        System.out.println(line);
    }
}