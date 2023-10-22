package org.danilkha.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class CodeGenerator {

    private Random random;

    public CodeGenerator(){
        random = new Random();
    }
    public int generate6SignCode(){
        return random.nextInt(100_000, 1_000_000);
    }

    public String generateStringCode(int length){
        byte[] code = new byte[length];
        random.nextBytes(code);
        return Base64.getEncoder().encodeToString(code);
    }
}
