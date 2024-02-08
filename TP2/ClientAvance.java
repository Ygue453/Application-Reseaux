import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientAvance {
    static Scanner clavier = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        String ip;
        int port;
        if (args[0] == "EchoUDPClient")
            System.out.println("Yeeepeee !!!\n");
        if (args.length > 2){
            ip = args[1];
            port = Integer.parseInt(args[2]);
            client(ip, port);
        }
        else
            System.out.println("La syntaxe est : java ClientAvance.java EchoUDPClient [ip du serveur] [port du serveur].");
    }

    static public void client(String ip, int port) throws IOException {
        DatagramSocket c = new DatagramSocket();
        while (true) {
            System.out.println("Saisissez votre ligne :");
            String line = clavier.nextLine() + "\n";
            InetAddress localhost = InetAddress.getByName(ip);

            byte msg[] = line.getBytes();
            DatagramPacket out = new DatagramPacket(msg, msg.length, localhost, port);
            c.send(out);
        }
    }
}
