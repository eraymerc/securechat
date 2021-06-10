package encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSA() throws NoSuchAlgorithmException {
        System.out.println("\u001b[33mRSA Anahtar Çifti Oluşturuluyor...\u001b[0m");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getPublicKeyBase64 (){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    

    public String decrypt(String encrypted)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);

        byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedBytes);
        String decryptedMessage = new String(decryptedMessageBytes, "UTF-8");
        return decryptedMessage;

    }

    public static String encrypt(String secret, String publicKeyBase64) throws Exception{

        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);

        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] secretBytes = secret.getBytes("utf-8");
        byte[] encrypted = encryptCipher.doFinal(secretBytes);
        String encoded = Base64.getEncoder().encodeToString(encrypted);
        return encoded;

    }
}
