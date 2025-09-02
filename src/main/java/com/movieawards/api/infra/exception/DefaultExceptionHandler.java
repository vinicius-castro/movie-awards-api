package com.movieawards.api.infra.exception;

import com.movieawards.api.application.exception.MovieAwardNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(MovieAwardNotFoundException.class)
    public ResponseEntity<Object> handleMovieAwardNotFoundException(MovieAwardNotFoundException ex) {
        logger.error("m=handleMovieAwardNotFoundException, message={}", ex.getMessage(), ex);
        return ResponseEntity.notFound().build();
    }
}
