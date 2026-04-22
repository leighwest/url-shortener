package com.example.url_shortener.domain;

import java.util.regex.Pattern;

public record ShortCode(String value) {

    private static final Pattern VALID = Pattern.compile("^[0-9a-zA-Z]{1,10}$");

    public ShortCode {
        if (value == null || !VALID.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid short code: " + value);
        }
    }
}
