import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESECBExample{
    public static void main(String[] args) {
        String oriMessage = "This is a confidential message";
        int[] keySizes = {128, 192, 256};

        System.out.println("----------------------");
        System.out.println(" \tAES Encryption\t ");
        System.out.println("----------------------");
        System.out.println("Original Message: " + oriMessage);

        try {
            for (int size : keySizes) {
                byte[] keyBytes = generateRandomKey(size);
                SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

                byte[] cipherText = encrypt(oriMessage, secretKey);
                System.out.printf("AES-%d (%d-byte key):\n", size, size / 8);
                System.out.println("Random Key (in Hex): " + bytesToHex(keyBytes));
                System.out.println("Ciphertext (in Hex): " + bytesToHex(cipherText));
                System.out.println("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(String message, SecretKey key) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message.getBytes());
    }

    private static byte[] generateRandomKey(int n) throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keyGen.init(n, random);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    private static String bytesToHex(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
