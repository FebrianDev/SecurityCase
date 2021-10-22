package server;

import client.Client;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// server.server.ClientHandler class
class ClientHandler extends Thread {
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;

    boolean run = true;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        while (run) {
            try {
                //Creating a Cipher object
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

                cipher.init(Cipher.ENCRYPT_MODE, Client.getPublicKeyClient());
                //Adding data to the cipher
                byte[] input = "Server ".getBytes(StandardCharsets.UTF_8);
                cipher.update(input);
                //Encrypting the data
                byte[] cipherText = cipher.doFinal();

                dos.writeUTF(Server.createAESKey().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
