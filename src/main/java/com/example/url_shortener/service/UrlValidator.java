package com.example.url_shortener.service;

import com.example.url_shortener.exception.InvalidUrlException;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class UrlValidator {
    public void validate(String url) {
        if (url == null || url.isBlank()) {
            throw new InvalidUrlException("URL must not be blank");
        }
        try {
            URI.create(url);
        } catch (IllegalArgumentException e) {
            throw new InvalidUrlException("URL is malformed: " + url);
        }
        if (!url.startsWith("http:") && !url.startsWith("https:")) {
            throw new InvalidUrlException("URL must use http or https: " + url);
        }
    }
}
