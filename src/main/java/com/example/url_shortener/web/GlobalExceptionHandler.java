package com.example.url_shortener.web;

import com.example.url_shortener.exception.InvalidUrlException;
import com.example.url_shortener.exception.ShortUrlNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ShortUrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(ShortUrlNotFoundException ex) {
        log.warn("Short code not found: {}", ex.getMessage());
        return new ErrorResponse("Short code not found", ex.getMessage());
    }

    @ExceptionHandler(InvalidUrlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidUrl(InvalidUrlException ex) {
        log.warn("Invalid URL: {}", ex.getMessage());
        return new ErrorResponse("Invalid URL", ex.getMessage());
    }
}
