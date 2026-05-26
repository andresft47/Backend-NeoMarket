package com.neomarket.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class PasswordHasher {

    private static final String SALT_PREFIX = "NeoMarketSalt:";

    public String encode(String rawPassword) {
        return hash(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) return false;
        return encodedPassword.equals(hash(rawPassword));
    }

    private String hash(String rawPassword) {
        return rawPassword;
    }
}
