import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Stress1 {
    private int nbClient;

    public Stress1(int nbClient){
        this.nbClient = nbClient;
    }
    public static void main(String[] args) throws UnknownHostException, IOException {
        if (args.length == 1){
            long time = System.nanoTime();
            try {
                Stress1 stress = new Stress1(Integer.parseInt(args[0]));
                for (int i = 0; i < stress.nbClient; i++){
                    stress.clientStressant(i + 1);
                }
            } catch (Exception e) {
                System.out.println("Synthaxe : java Stress1 [nbClient]");
            }
            System.out.println((System.nanoTime() - time)/1000000 + " millisecondes");
        }
        else {
            System.out.println("Error : La synthaxe est java Stress1 [nb clients stressants]");
        }
    }

    public void clientStressant(int n) throws UnknownHostException, IOException{
        Socket client = new Socket("localhost", 1234);
        OutputStream output = client.getOutputStream();

        String msg = "Client stress1 n°" + n;
        output.write(msg.getBytes());

        client.close();
    }
}
