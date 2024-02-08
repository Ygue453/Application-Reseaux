import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class BonusScriptJava {
    private static Scanner clavier = new Scanner(System.in);
    private static String msgs[] = new String[2];
    public static void main(String[] args) throws IOException, InterruptedException{
        //Auto-test serveur java
        Thread serveur = new Thread(){
            public void run(){
                //lancement du serveur
                try {
                    serveur(1234);
                } catch (IOException e) {
                    System.out.println("Une erreur est survenue sur le serveur");
                }
            }
        };

        Thread client = new Thread(){
            public void run(){
                try {
                    client("localhost", 1234);
                } catch (IOException e) {
                    System.out.println("Une erreur est survenue sur le client");
                }
            }
        };

        serveur.start();
        client.start();

        //Auto-test serveur python
        //client("localhost", 1234);

        while (msgs[0] == null || msgs[1] == null)
            Thread.sleep(10);
        System.out.println("Auto-test Java terminé !");
        
        //Auto-test serveur python
        Thread.sleep(2000);//attendre que le serveur python soit lancé
        client("localhost", 1234);
        System.out.println("Fini !");
    }

    public static void serveur(int port) throws IOException{
        DatagramSocket s = new DatagramSocket(port);
        System.out.println("Serveur Java lancé");
        for (int i = 0; i < 2; i++){
            byte msg[] = new byte[600];
            DatagramPacket in = new DatagramPacket(msg, msg.length);
            s.receive(in);
            String inData = new String(in.getData());
            msgs[i] = new String(in.getData());
            inData = ">" + inData;
            System.out.println(inData);
            s.send(new DatagramPacket(inData.getBytes(), inData.getBytes().length, in.getAddress(), in.getPort()));
        }
        s.close();
        System.out.println("Serveur Java fini !");
    }

    public static void client(String ip, int port) throws IOException{
        DatagramSocket c = new DatagramSocket();

        String line = "Message client java";
        byte msg[] = line.getBytes();

        InetAddress localhost = InetAddress.getByName(ip);

        DatagramPacket out = new DatagramPacket(msg, msg.length, localhost, port);
        c.send(out);

        c.close();
        System.out.println("Client Java fini !");
    }
}
