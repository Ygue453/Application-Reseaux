import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientAvance{
    static Scanner clavier = new Scanner(System.in);
    static String ip;
    static int port;
    public static void main(String[] args) throws IOException{
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
            System.out.println("Erreur ! ------- La synthaxe est la suivante : java ClientAvance.java EchoTCPClient [serveur] [port]");
        }
    }

    public static void client(String ip, int port) throws IOException{
        Socket client = new Socket("localhost", 1234);
        PrintStream output = new PrintStream(client.getOutputStream());
        while (true) {
            System.out.println("Entrez votre ligne :");
            String line = clavier.nextLine();
            output.println(line);
        }
    }
}