import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Serveur {
    String ip;
    int port;

    public static void main(String[] args) throws IOException{
        Serveur serveur = new Serveur();
        serveur.serveur();
    }

    public Serveur(){
        this.ip = "localhost";
        this.port = 1234;
    }

    public void serveur() throws IOException{
        try {
            ServerSocketChannel serveur = ServerSocketChannel.open();
            Selector selector = Selector.open();
            serveur.bind(new InetSocketAddress(ip, port));
            serveur.configureBlocking(false);
            serveur.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server up\n-------------------\n");
            
            while (true){
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()){
                        handleClient(serveur, selector);
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = client.read(buffer);

                        if (bytesRead < 1){
                            key.cancel();
                            client.close();
                            System.err.println("No data found");
                        }

                        if (bytesRead > 0) {
                            buffer.flip();

                            byte[] bytes = new byte[bytesRead];
                            buffer.get(bytes);
                            String message = ">" + new String(bytes, "UTF-8");
                            echoServer(message);
                            client.write(ByteBuffer.wrap(message.getBytes("UTF-8")));

                            key.cancel();
                            client.close();
                        }
                        buffer.clear();
                    }
                }
            }
            } catch (IOException e) {
            System.err.println("Error : The server has encouter a problem.");
        }
    }

    private void handleClient(ServerSocketChannel serveur, Selector selector) throws IOException{
        SocketChannel client = serveur.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);

        String clientConnection = "" + client.getRemoteAddress();
        echoServer("New Connection from : " + clientConnection.substring(1));
    }

    private static void echoServer(String line){
        System.out.println(line);
    }
}
