package com.example.url_shortener.service;

import com.example.url_shortener.domain.ShortCode;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomCodeGenerator implements ShortCodeGenerator {
    private static final String CHARS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public ShortCode generate() {
        var sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return new ShortCode(sb.toString());
    }
}
