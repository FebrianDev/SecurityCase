import java.security.*;

public class VerifySignature {
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(512);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the privatekey from the key pair
        PrivateKey privKey = pair.getPrivate();

        System.out.println("Private Key "+privKey.getEncoded());

        System.out.println("\nBatas\n");
        PublicKey publicKey = pair.getPublic();

        System.out.println("Public Key "+publicKey.getEncoded());
    }
}
