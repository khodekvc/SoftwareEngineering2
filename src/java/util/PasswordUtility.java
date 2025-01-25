package util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Shei
 */
public class PasswordUtility {
    public static String encryptPassword(String password, String encryptionSecretKey, String encryptionCipher) {
        try {
            Cipher cipher = Cipher.getInstance(encryptionCipher);
            SecretKeySpec secretKey = new SecretKeySpec(encryptionSecretKey.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            
            return Base64.encodeBase64String(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptPassword(String encryptedPassword, String encryptionSecretKey, String encryptionCipher) {
        try {
            Cipher cipher = Cipher.getInstance(encryptionCipher);
            SecretKeySpec secretKey = new SecretKeySpec(encryptionSecretKey.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] decodedBytes = Base64.decodeBase64(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
