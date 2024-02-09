import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Stress1 {
    private int nbClient;
    private double[] values;

    public Stress1(int nbClient){
        this.nbClient = nbClient;
        this.values = new double[nbClient];
    }
    public static void main(String[] args) throws UnknownHostException, IOException {
        long time = System.nanoTime();
        if (args.length == 1){
            try {
                Stress1 stress = new Stress1(Integer.parseInt(args[0]));
                for (int i = 0; i < stress.nbClient; i++){
                    stress.clientStressant(i + 1);
                }
                stress.creerCSV();
            } catch (Exception e) {
                System.out.println("Synthaxe : java Stress1 [nbClient]");
            }
        }
        else {
            System.out.println("Error : La synthaxe est java Stress1 [nb clients stressants]");
        }
        double t = (System.nanoTime() - time);
        System.out.println("Total : " + t/1000000 + " millisecondes");
    }

    public void clientStressant(int n) throws UnknownHostException, IOException{
        Socket client = new Socket("localhost", 1234);
        OutputStream output = client.getOutputStream();
        InputStream input = client.getInputStream();

        String msg = "Client stress1 n°" + n;
        output.write(msg.getBytes());

        long time = System.nanoTime();

        byte[] buffer = new byte[8192];
        int bytes = -1;
        if ((bytes = input.read(buffer)) != -1){
            double t = (System.nanoTime() - time);
            values[n-1] = t;
            System.out.println("Reponse client " + n + " : " + t/1000000 + " milllisecondes");
        }

        client.close();
    }

    public void creerCSV() throws IOException{
        FileWriter fileWriter = new FileWriter("PingServeurTCP.csv");
        fileWriter.append("N° client;");
        for (int i = 0; i < this.nbClient; i++){
            fileWriter.append("client : " + (i + 1) + ";");
        }
        fileWriter.append("\nLatence du Serveur en millisecondes;");
        for (int i = 0; i < this.nbClient; i++){
            String value = values[i]/1000000 + "";
            value = value.replace('.', ',');
            fileWriter.append(value + ";");
        }
        fileWriter.close();
    }
}
