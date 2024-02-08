package Test;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTCPHandler extends Thread{
    private Socket client;

    public ClientTCPHandler(Socket client){
        this.client = client;
    }

    public void run(){
        //int nbClient = ServeurTCP.getNbClient();
        
        try {
            InputStream input = this.client.getInputStream();
            //OutputStream output = this.client.getOutputStream();

            /*String initMsgString = "Client n°" + nbClient;
    
            byte[] initMsg = initMsgString.getBytes();
            output.write(initMsg);*/
    
            byte[] buffer = new byte[4096];
            int bytes = 0;
    
            while ((bytes = input.read(buffer)) != -1) {
                String message = ">" + new String(buffer, 0, bytes);
                //ServeurTCP.echoServer("Message from client n°" + nbClient + " " + message);
                System.out.println(message);
                /*byte[] newBuffer = message.getBytes();
                output.write(newBuffer);*/
            }
        } catch (IOException e) {
            System.out.println("Error on input or output");
        }
    }
}
