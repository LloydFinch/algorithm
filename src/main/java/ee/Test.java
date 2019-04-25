package ee;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;

public class Test {
    public static void main(String[] args) {
        testLocalSocket();
    }

    private static void testLocalSocket() {
        try {
            int port = 8080;
            ServerSocket serverSocket = new ServerSocket(port);
            SocketAddress socketAddress = serverSocket.getLocalSocketAddress();
            println(serverSocket.getLocalPort());
            println(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void println(Object object) {
        System.out.println(object);
    }
}
