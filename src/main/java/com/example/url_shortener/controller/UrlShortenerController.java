package com.example.url_shortener.controller;

import com.example.url_shortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlShortenerController {
    private final UrlShortenerService service;

    @Value("${app.base-url}")
    private String baseUrl;

    @PostMapping("/api/shorten")
    public ShortenResponse shorten(@RequestBody ShortenRequest req) {
        var code = service.shorten(req.url());
        return new ShortenResponse(baseUrl + "/" + code.value());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        var longUrl = service.resolve(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }

    public record ShortenRequest(String url) {}
    public record ShortenResponse(String shortUrl) {}
}
