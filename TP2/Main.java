import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {
    static Scanner clavier = new Scanner(System.in);
    static String ip;
    static int port;
    
    public static void main(String[] args) throws IOException {
        //Coté client
        
        if (args.length > 0){
            if (args.length < 2){
                System.out.println("L'ip n'est pas valide.\nLa syntaxe du client est : java Main.java EchoUDPClient [ip du serveur] [port du serveur].");
                return;
            }
            ip = args[1];
            if (args.length < 3){
                System.out.println("Le port n'est pas valide.\nLa syntaxe du client est : java Main.java EchoUDPClient [ip du serveur] [port du serveur].");
                return;
            }
            port = Integer.parseInt(args[2]);
            client();
        }

        //Coté serveur
        else{
            serveur();
        }
    }

    public static void client() throws IOException{
        DatagramSocket c = new DatagramSocket();
        while (true){
            System.out.println("Saisissez votre ligne :");
            String line = clavier.nextLine();
            line += "\n";
            byte msg[] = line.getBytes();

            InetAddress localhost = InetAddress.getByName(ip);
            DatagramPacket out = new DatagramPacket(msg, msg.length, localhost, port);
            c.send(out);

            c.receive(out);
            String inData = new String(out.getData());
            System.out.println(inData);
        }
    }

    public static void serveur() throws IOException{
        DatagramSocket s = new DatagramSocket(1234);
        while (true){
            byte msg[]=new byte[600];
            DatagramPacket in = new DatagramPacket(msg, msg.length);
            s.receive(in);
            String inData = new String(in.getData());
            inData = ">" + inData;
            System.out.println(inData);
            s.send(new DatagramPacket(inData.getBytes(), inData.getBytes().length, in.getAddress(), in.getPort()));
        }
    }
}
