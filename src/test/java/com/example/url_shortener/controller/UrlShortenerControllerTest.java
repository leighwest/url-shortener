package com.example.url_shortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shortenValidUrl() throws Exception {
        mockMvc.perform(post("/api/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"url": "https://www.example.com"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").isNotEmpty());
    }

    @Test
    void shortenThenRedirect() throws Exception {
        var result = mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"url": "https://www.example.com"}
                    """))
                .andExpect(status().isOk())
                .andReturn();

        var shortUrl = new ObjectMapper()
                .readTree(result.getResponse().getContentAsString())
                .get("shortUrl")
                .asText();

        var shortCode = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);

        mockMvc.perform(get("/" + shortCode))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://www.example.com"));
    }

    @Test
    void unknownCodeReturns404() throws Exception {
        mockMvc.perform(get("/unknownCode"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").isNotEmpty());
    }

    @Test
    void blankUrlReturns400() throws Exception {
        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"url": ""}
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isNotEmpty());
    }

    @Test
    void invalidUrlReturns400() throws Exception {
        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"url": "not-a-url"}
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nonHttpUrlReturns400() throws Exception {
        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"url": "ftp://example.com"}
                        """))
                .andExpect(status().isBadRequest());
    }
}