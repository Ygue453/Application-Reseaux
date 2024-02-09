import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Stress1 {

    

    static int nbIteration = 6;
    static double[] values = new double[nbIteration];
    static int[] iteration = new int[nbIteration];

    private int nbClient;

    public Stress1(int nbClient){
        this.nbClient = nbClient;
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        /*
        long time = System.nanotime();
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
            System.out.println("Synthaxe : java Stress1 [nbClient]");
        }
        double t = (System.nanoTime() - time);
        System.out.println("Total : " + t/1000000 + " millisecondes");
        */

        
        iteration[0] = 1;
        iteration[1] = 2;
        iteration[2] = 10;
        iteration[3] = 100;
        iteration[4] = 1000;
        iteration[5] = 5000;

        for (int i = 0; i < iteration.length; i++){
            Stress1 stress = new Stress1(iteration[i]);
            long time = System.nanoTime();
            for (int y = 0; y < iteration[i]; y++){
                stress.clientStressant(y+1);
            }
            double t = (System.nanoTime() - time);
            values[i] = t;
            System.out.println("Total : " + t/1000000 + " millisecondes");
        }
        creerCSV();
    }

    public void clientStressant(int n) throws UnknownHostException, IOException{
        Socket client = new Socket("localhost", 1234);
        OutputStream output = client.getOutputStream();
        InputStream input = client.getInputStream();

        String msg = "Client stress1 n°" + n;
        output.write(msg.getBytes());

        //long time = System.nanoTime();

        byte[] buffer = new byte[8192];
        int bytes = -1;
        if ((bytes = input.read(buffer)) != -1){
            /*double t = (System.nanoTime() - time);
            values[n-1] = t;
            System.out.println("Reponse client " + n + " : " + t/1000000 + " milllisecondes");*/
        }

        client.close();
    }

    public static void creerCSV() throws IOException{
        FileWriter fileWriter = new FileWriter("PingServeurTCP.csv");
        fileWriter.append("Nombre de clients;");
        for (int i = 0; i < nbIteration; i++){
            fileWriter.append(iteration[i] + " clients;");
        }
        fileWriter.append("\nTemps d'execution du Serveur en millisecondes;");
        for (int i = 0; i < nbIteration; i++){
            String value = values[i]/1000000 + "";
            value = value.replace('.', ',');
            fileWriter.append(value + ";");
        }
        fileWriter.close();
    }
}
