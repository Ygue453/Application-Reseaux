import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDPEchoJava {
    static Scanner clavier = new Scanner(System.in);
    static private String ip = "localhost"; //ip de la machine
    public static void main(String[] args) throws IOException {
        client();
    }

    static public void client() throws IOException {
        DatagramSocket c = new DatagramSocket();
        System.out.println("Saisissez votre ligne :");
        String line = clavier.nextLine() + "\n";
        byte msg[] = line.getBytes();
        InetAddress localhost = InetAddress.getByName(ip);
        DatagramPacket out = new DatagramPacket(msg, msg.length, localhost, 1234);
        c.send(out);
    }
}
