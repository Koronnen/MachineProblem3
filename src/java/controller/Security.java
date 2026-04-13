package controller;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;

public class Security {
    public static String encrypt(String strToEncrypt, ServletContext context) {
        //Get key
        String rawKey = context.getInitParameter("SecurityKey");
        final byte[] key = rawKey.getBytes();
        String encryptedString = null;
        try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
                System.err.println(e.getMessage());
        }
        return encryptedString;
    }

    public static String decrypt(String codeDecrypt, ServletContext context){
        //Get key
        String rawKey = context.getInitParameter("SecurityKey");
        final byte[] key = rawKey.getBytes();
        String decryptedString = null;
        try{
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                decryptedString = new String(cipher.doFinal(Base64.decodeBase64(codeDecrypt)));
        } catch (Exception e) {
                System.err.println(e.getMessage());
        }
        return decryptedString;
    }	
}