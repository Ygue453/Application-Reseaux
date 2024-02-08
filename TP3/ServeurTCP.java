import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServeurTCP {
    static Scanner clavier = new Scanner(System.in);
    static String ip = "localhost";
    static int port = 1234;

    public static void main(String[] args) throws IOException, InterruptedException{
        if (args.length > 0 && args[0].compareTo("EchoTCPClient") == 0){
            if (args.length == 3){
                try {
                    ip = args[1];
                } catch (Exception e) {
                    System.out.println("Erreur ! ------- La synthaxe est la suivante : java ClientAvance.java EchoTCPClient [serveur] [port]");
                }
                try {
                    port = Integer.parseInt(args[2]);
                    try {
                        client(ip, port);
                    } catch (Exception e) {
                        System.out.println("La connexion n'a pu être établie");
                    }
                } catch (Exception e) {
                    System.out.println("Erreur ! ------- La synthaxe est la suivante : java ClientAvance.java EchoTCPClient [serveur] [port]");
                }
            }
            else {
                System.out.println("Erreur ! ------- La synthaxe est la suivante : java ClientAvance.java EchoTCPClient [serveur] [port]");
            }
        }
        else{
            serveur();
        }
    }

    public static void serveur() throws IOException{
        ServerSocket serveur = new ServerSocket(port, 10, InetAddress.getByName(ip));
        System.out.println("Server up\n-------------------\n");

        System.out.println("Waiting for client ...");
        Socket client = serveur.accept();
        System.out.println("Client connected !");

        InputStream input = client.getInputStream();
        OutputStream output = client.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytes = 0;

        while ((bytes = input.read(buffer)) != -1) {
            String message = ">" + new String(buffer, 0, bytes);
            System.out.println(message);
            byte[] newBuffer = message.getBytes();
            output.write(newBuffer);
        }

        client.close();
    }

    public static void client(String ip, int port) throws IOException, InterruptedException{
        Socket client = new Socket(ip, port);

        OutputStream output = client.getOutputStream();
        InputStream input = client.getInputStream();

        while (true) {
            System.out.println("Enter your line :");
            String line = clavier.nextLine();
            output.write(line.getBytes());

            byte[] buffer = new byte[8192];
            int bytes = -1;
            if ((bytes = input.read(buffer)) != -1){
                String message = new String(buffer, 0, bytes);
                System.out.println(message + "\n");
            }
        }
    }
}
