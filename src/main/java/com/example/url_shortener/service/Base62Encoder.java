package com.example.url_shortener.service;

import com.example.url_shortener.domain.ShortCode;
import org.springframework.stereotype.Component;

@Component
// algo taken from this: https://mojoauth.com/binary-encoding-decoding/base62-with-java#encoding-data-to-base62-in-java
public class Base62Encoder implements ShortCodeGenerator {
    private static final String CHARS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public ShortCode generate(long id) {
        if (id < 0) throw new IllegalArgumentException("id must be non-negative");
        if (id == 0) return new ShortCode("0");
        var sb = new StringBuilder();
        while (id > 0) {
            sb.append(CHARS.charAt((int) (id % 62)));
            id /= 62;
        }
        return new ShortCode(sb.reverse().toString());
    }
}
