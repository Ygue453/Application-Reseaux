import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThreaded implements Runnable {
    private Socket client;

    public ClientThreaded(Socket client){
        this.client = client;
    }

    public void run(){
        try {
            serverAction();
        } catch (IOException e) {
            System.out.println("Error : probleme with server action");
        }
    }

    public void serverAction() throws IOException{
        System.out.println("Client connected !");
        ServeurTCP.incrementNbClient();

        InputStream input = client.getInputStream();
        OutputStream output = client.getOutputStream();
        String initMsgString = "Client n°" + ServeurTCP.getNbClient();
        byte[] initMsg = initMsgString.getBytes();
        output.write(initMsg);
        byte[] buffer = new byte[4096];
        int bytes = 0;

        while ((bytes = input.read(buffer)) != -1) {
            String message = ">" + new String(buffer, 0, bytes);
            ServeurTCP.echoServer(message);
            byte[] newBuffer = message.getBytes();
            output.write(newBuffer);
        }
    }
}
