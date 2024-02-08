import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServeurUDP {

    public static void main(String[] args) throws IOException {
        serveur();
    }

    public static void serveur() throws IOException {
        DatagramSocket s = new DatagramSocket(1234);
        while (true){
            byte msg[]=new byte[500];
            DatagramPacket in = new DatagramPacket(msg, msg.length);
            s.receive(in);
            String inData = new String(in.getData());
            System.out.println(">"+inData);
        }
    }
}
