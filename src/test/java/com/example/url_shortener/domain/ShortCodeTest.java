package com.example.url_shortener.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShortCodeTest {

    @Test
    void validCodeIsAccepted() {
        assertThatNoException().isThrownBy(() -> new ShortCode("abc123"));
    }

    @Test
    void nullIsRejected() {
        assertThatThrownBy(() -> new ShortCode(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void emptyStringIsRejected() {
        assertThatThrownBy(() -> new ShortCode(""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}