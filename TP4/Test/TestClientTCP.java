package Test;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TestClientTCP{
    static Scanner clavier = new Scanner(System.in);
    static String ip;
    static int port;
    public static void main(String[] args) throws IOException{
        if (args.length == 2){
            try {
                ip = args[0];
            } catch (Exception e) {
                System.out.println("Erreur ! ------- La synthaxe est la suivante : java ClientTCP [serveur] [port]");
            }
            try {
                port = Integer.parseInt(args[1]);
                try {
                    client(ip, port);
                } catch (Exception e) {
                    System.out.println("La connexion n'a pu être établie");
                }
            } catch (Exception e) {
                System.out.println("Erreur ! ------- La synthaxe est la suivante : java ClientTCP [serveur] [port]");
            }
        }
        else {
            System.out.println("Erreur ! ------- La synthaxe est la suivante : java ClientTCP [serveur] [port]");
        }
    }

    public static void client(String ip, int port) throws IOException{
        Socket client = new Socket(ip, port);

        OutputStream output = client.getOutputStream();
        InputStream input = client.getInputStream();

        byte[] buffer = new byte[8192];
        int bytes = -1;
        if ((bytes = input.read(buffer)) != -1){
            String message = new String(buffer, 0, bytes);
            System.out.println(message + "\n");
        }

        while (true) {
            System.out.println("Enter your line :");
            String line = clavier.nextLine();
            output.write(line.getBytes());

            buffer = new byte[8192];
            bytes = -1;
            if ((bytes = input.read(buffer)) != -1){
                String message = new String(buffer, 0, bytes);
                System.out.println(message + "\n");
            }
        }
    }
}