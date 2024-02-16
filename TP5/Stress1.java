import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Stress1 {

    private int nbClient;

    public Stress1(int nbClient){
        this.nbClient = nbClient;
    }
    public static void main(String[] args) {
        if (args.length == 1){
            try {
                Stress1 stress = new Stress1(Integer.parseInt(args[0]));
                for (int i = 0; i < stress.nbClient; i++){
                    stress.clientStressant(i + 1);
                }
            } catch (Exception e) {
                System.out.println("Synthaxe : java Stress1 [nbClient]");
            }
        }
        else {
            System.out.println("Synthaxe : java Stress1 [nbClient]");
        }
    }

    private void clientStressant(int n) throws UnknownHostException, IOException{
        Socket client = new Socket("localhost", 1234);
        OutputStream output = client.getOutputStream();
        InputStream input = client.getInputStream();

        String msg = "Client stress1 n°" + n;
        output.write(msg.getBytes());


        byte[] buffer = new byte[8192];
        int bytes = -1;
        if ((bytes = input.read(buffer)) != -1){
            System.out.println(new String(buffer, 0, bytes));
        }

        client.close();
    }
}
