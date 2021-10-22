package server;

import client.Client;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Server {
    public void start() throws IOException, NoSuchAlgorithmException {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(6666);
        InitKey();
        // running infinite loop for getting

        // client request
        while (true) {
            Socket s = null;

            try {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);
                System.out.println("Public Key Client "+ Client.getPublicKeyClient());
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();

            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }

    private void InitKey() throws NoSuchAlgorithmException, IOException {
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(512);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the privatekey from the key pair
        PrivateKey privKey = pair.getPrivate();

        getPublicKeyServer();

    }

    public static PublicKey getPublicKeyServer() throws NoSuchAlgorithmException {
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(512);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        PublicKey publicKey = pair.getPublic();

        return publicKey;
    }

    // Function to create a secret key
    public static SecretKey createAESKey()
            throws Exception
    {

        // Creating a new instance of
        // SecureRandom class.
        SecureRandom securerandom
                = new SecureRandom();

        // Passing the string to
        // KeyGenerator
        KeyGenerator keygenerator
                = KeyGenerator.getInstance("AES");

        // Initializing the KeyGenerator
        // with 256 bits.
        keygenerator.init(256, securerandom);
        SecretKey key = keygenerator.generateKey();
        return key;
    }

}
