import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTCPHandler extends Thread{

    //Uncomment if you want to use the ServeurTCP with ClientTCP

    private Socket client;

    public ClientTCPHandler(Socket client){
        this.client = client;
    }

    public void run(){
        
        try {
            InputStream input = this.client.getInputStream();
            //OutputStream output = this.client.getOutputStream();
    
            byte[] buffer = new byte[4096];
            int bytes = 0;
    
            while ((bytes = input.read(buffer)) != -1) {
                String message = ">" + new String(buffer, 0, bytes);
                System.out.println(message);

                /*byte[] newBuffer = message.getBytes();
                output.write(newBuffer);*/
            }
        } catch (IOException e) {
            System.out.println("Error on input or output");
        }
    }
}
