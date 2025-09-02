package com.movieawards.api.infra.startup;

import com.movieawards.api.infra.batch.MovieAwardFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

class RunnerTest {

    private MovieAwardFileService movieAwardFileService;
    private Runner runner;

    @BeforeEach
    void setUp() {
        movieAwardFileService = mock(MovieAwardFileService.class);
        runner = new Runner(movieAwardFileService);
    }

    @Test
    void shouldTest() {
        assertDoesNotThrow(() -> runner.run(""));
        verify(movieAwardFileService, only()).createMovieAwards(anyString());
    }

}