package EchoService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP Based Echo Service based on RFC862.
 *
 * @author taiprogramer
 * */

class Server {
    // to avoid root permission required on Linux
    private static final int ECHO_PORT = 3000;
    public static void main(String args[]) {
        try (ServerSocket server = new ServerSocket(ECHO_PORT)) {
            System.out.println("Server has been started");
            while(true) {
                socketHandler(server.accept());
            }
        } catch(IOException e) {
            System.err.println(e);
        }
    }

    private static void socketHandler(final Socket socket) {
        Thread t = new Thread(() -> {
            String ip = socket.getInetAddress().toString();
            int port = socket.getPort();
            System.out.println(String.format("Got new connection"));
            System.out.println(
                    String.format("Addr: %s:%d | Connected", ip, port));
            try(
                    InputStream iStream  = socket.getInputStream();
                    OutputStream oStream = socket.getOutputStream();
               ) {
                while(true) {
                    int ch = iStream.read();
                    oStream.write(ch);
                }
            } catch(IOException e) {
                System.out.println(
                        String.format(
                            "Addr: %s:%d | Disconnected", ip, port));
            }
        });
        t.start();
    }
}
