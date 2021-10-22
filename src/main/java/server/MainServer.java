package server;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainServer {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Server server = new Server();
        server.start();
    }
}
