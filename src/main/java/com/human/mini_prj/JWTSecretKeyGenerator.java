package com.human.mini_prj;

import java.security.SecureRandom;
import java.util.Base64;

// AI가 말아준 랜덤 JWT_SECRET 만들기 클래쓰~~
public class SecretKeyGenerator {
    public static void main(String[] args) {
        byte[] key = new byte[64];

        new SecureRandom().nextBytes(key);

        String secret = Base64.getEncoder().encodeToString(key);

        System.out.println(secret);
    }
}