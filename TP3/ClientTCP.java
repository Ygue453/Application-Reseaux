import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP{
    static Scanner clavier = new Scanner(System.in);
    public static void main(String[] args) throws IOException{
        client();
    }

    public static void client() throws IOException{
        Socket client = new Socket("localhost", 1234);
        PrintStream output = new PrintStream(client.getOutputStream());
        System.out.println("Entrez votre ligne :");
        String line = clavier.nextLine();
        output.println(line);
    }
}