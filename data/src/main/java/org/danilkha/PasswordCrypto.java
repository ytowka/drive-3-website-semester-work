package org.danilkha;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordCrypto {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final static MessageDigest messageDigest;
    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] hashPassword(String password){
        return messageDigest.digest(password.getBytes(CHARSET));
    }

    public static String encodePassword(byte[] password){
        return Base64.getEncoder().encodeToString(password);
    }

    public static byte[] decodePassword(String password){
        return Base64.getDecoder().decode(password);
    }
}
