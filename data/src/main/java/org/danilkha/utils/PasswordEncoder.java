package org.danilkha.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncoder {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private final MessageDigest messageDigest;

    public PasswordEncoder(MessageDigest messageDigest){
        this.messageDigest = messageDigest;
    }


    // messageDigest = MessageDigest.getInstance("SHA-256");


    public byte[] hashPassword(String password){
        return messageDigest.digest(password.getBytes(CHARSET));
    }

    public String encodePassword(byte[] password){
        return Base64.getEncoder().encodeToString(password);
    }

    public byte[] decodePassword(String password){
        return Base64.getDecoder().decode(password);
    }
}
