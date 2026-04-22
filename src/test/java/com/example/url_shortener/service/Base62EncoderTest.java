package com.example.url_shortener.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Base62EncoderTest {

    private final Base62Encoder encoder = new Base62Encoder();

    @Test
    void zeroEncodesToZero() {
        assertThat(encoder.generate(0).value()).isEqualTo("0");
    }

    @Test
    void oneEncodesToOne() {
        assertThat(encoder.generate(1).value()).isEqualTo("1");
    }

    @Test
    void sixtyTwoEncodesToTen() {
        assertThat(encoder.generate(62).value()).isEqualTo("10");
    }

    @Test
    void negativeIdThrowsException() {
        assertThatThrownBy(() -> encoder.generate(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}