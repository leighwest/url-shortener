package com.example.url_shortener.service;

import com.example.url_shortener.domain.ShortCode;

public interface ShortCodeGenerator {
    ShortCode generate(long id);
}
