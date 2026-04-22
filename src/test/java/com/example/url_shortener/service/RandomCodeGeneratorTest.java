package com.example.url_shortener.service;

import com.example.url_shortener.domain.ShortCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomCodeGeneratorTest {

    private final RandomCodeGenerator generator = new RandomCodeGenerator();

    @Test
    void generatedCodeIsValid() {
        ShortCode code = generator.generate();
        assertThat(code.value()).matches("[0-9a-zA-Z]{6}");
    }

    @Test
    void generatedCodesAreUnique() {
        ShortCode first = generator.generate();
        ShortCode second = generator.generate();
        assertThat(first.value()).isNotEqualTo(second.value());
    }
}