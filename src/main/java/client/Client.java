package client;

import server.Server;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Scanner;

public class Client {
    private Socket s;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Scanner scn;

    private boolean run = true;

    public void start() {
        try {
            scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            s = new Socket(ip, 6666);

            // obtaining input and out streams
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            //Creating a Cipher object
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.ENCRYPT_MODE, getPublicKeyClient());

            System.out.println("Public Key Server : " + Server.getPublicKeyServer());
            //Adding data to the cipher
            byte[] input = getPublicKeyClient().getEncoded();
            cipher.update(input);
            //Encrypting the data
            byte[] cipherText = cipher.doFinal();

            dos.writeUTF(cipherText.toString());

            // the following loop performs the exchange of
            // information between client and client handler
            while (run) {
                try {
                    String data = scn.nextLine();
                    Cipher cipher2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    dis.readUTF();
                    cipher.init(Cipher.ENCRYPT_MODE, Server.createAESKey());
                    //Adding data to the cipher
                    byte[] send = data.getBytes(StandardCharsets.UTF_8);
                    cipher.update(send);
                    //Encrypting the data
                    byte[] cipherText2 = cipher.doFinal();

                    dos.writeUTF(cipherText2.toString());

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
            // closing resources
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPrivateKey() throws NoSuchAlgorithmException, IOException {
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(512);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the privatekey from the key pair
        PrivateKey privKey = pair.getPrivate();

        return privKey.toString();
    }

    public static PublicKey getPublicKeyClient() throws NoSuchAlgorithmException {
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(512);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        PublicKey publicKey = pair.getPublic();
        return publicKey;
    }
}
