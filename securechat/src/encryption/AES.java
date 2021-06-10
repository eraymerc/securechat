package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    // AES-256
    private static SecretKeySpec anahtarGizli;
    private static byte[] anahtar;

    private static String sifre;

    public static void setPassword() throws Exception {
        generateRandomKey(10);
        setKey();
    }

    public static void setPassword(int passLength) throws Exception {
        generateRandomKey(passLength);
        setKey();
    }

    public static void setPassword(String _sifre) throws Exception {
        sifre = _sifre;
        setKey();
    }

    private static void setKey() throws Exception {

        MessageDigest sha = null;

        anahtar = sifre.getBytes("UTF-16");
        sha = MessageDigest.getInstance("SHA-1");
        anahtar = sha.digest(anahtar);
        anahtar = Arrays.copyOf(anahtar, 16);
        anahtarGizli = new SecretKeySpec(anahtar, "AES");

    }

    public static void generateRandomKey(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        sifre = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    }

    public static String getSifre() {
        return sifre;
    }

    public static String aes(int cipherMode, String metin) throws Exception {
        try {

            String cikti = "";
            byte[] metinB = metin.getBytes();

            Key secretKey = anahtarGizli;
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);

            if (cipherMode == Cipher.ENCRYPT_MODE) {

                cikti = Base64.getEncoder().encodeToString(cipher.doFinal(metinB));

            } else if (cipherMode == Cipher.DECRYPT_MODE) {

                cikti = new String(cipher.doFinal(Base64.getDecoder().decode(metin)));

            }

            return cikti;

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void aesSifreleDosya(int cipherMode, String dosya, String dosyaCikti) throws Exception {
        try {

            File inputFile = new File(dosya);
            Key secretKey = anahtarGizli;
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = null;
            if (cipherMode == Cipher.ENCRYPT_MODE) {
                outputBytes = Base64.getEncoder().encode(cipher.doFinal(inputBytes));
            } else if (cipherMode == Cipher.DECRYPT_MODE) {
                outputBytes = Base64.getDecoder().decode(inputBytes);

                outputBytes = cipher.doFinal(outputBytes);
            }

            File outputFile = new File(dosyaCikti);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
        }
    }

}
