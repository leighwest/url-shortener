package com.example.url_shortener.service;


import com.example.url_shortener.domain.ShortCode;
import com.example.url_shortener.exception.ShortUrlNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerService {
    private final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();
    private final ShortCodeGenerator codeGenerator;
    private final UrlValidator urlValidator;

    public ShortCode shorten(String longUrl) {
        urlValidator.validate(longUrl);
        ShortCode code;
        do {
            code = codeGenerator.generate();
        } while (store.containsKey(code.value()));
        store.put(code.value(), longUrl);
        log.info("Shortened URL to code={}", code.value());
        return code;
    }

    public String resolve(String shortCode) {
        String longUrl = store.get(shortCode);
        if (longUrl == null) {
            throw new ShortUrlNotFoundException(shortCode);
        }
        log.info("Resolved code={}", shortCode);
        return longUrl;
    }
}
