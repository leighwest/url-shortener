package com.example.url_shortener.service;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class UrlValidatorTest {

    private final UrlValidator validator = new UrlValidator();

    @Test
    void validHttpUrlIsAccepted() {
        assertThatNoException().isThrownBy(() -> validator.validate("http://example.com"));
    }

    @Test
    void validHttpsUrlIsAccepted() {
        assertThatNoException().isThrownBy(() -> validator.validate("https://example.com"));
    }

}