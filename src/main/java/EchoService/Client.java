package EchoService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Simple client to demonstrate Echo service.
 * 
 * @author taiprogramer
 * */

class Client {
    private static final int ECHO_PORT = 3000;
    public static void main(String args[]) {
	try (Socket socket = new Socket("127.0.0.1", ECHO_PORT)) {
	    InputStream iStream  = socket.getInputStream();
	    OutputStream oStream = socket.getOutputStream();
	    System.out.println("Connected");

	    int ch;
	    while(true) {
		ch = System.in.read();
		if (ch == '\r' || ch == '\n') {
		    continue;
		}
		if (ch == '.') {
		    break;
		}
		oStream.write(ch);
		System.out.println(String.format("me: %c", ch));
		ch = iStream.read();
		System.out.println(String.format("server: %c", ch));
	    }
	} catch(IOException e) {
	    System.err.println(e);
	}
    }
}
