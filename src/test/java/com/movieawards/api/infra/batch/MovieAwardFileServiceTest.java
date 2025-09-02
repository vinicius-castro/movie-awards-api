package com.movieawards.api.infra.batch;

import com.movieawards.api.domain.entity.MovieAward;
import com.movieawards.api.infra.repository.MovieAwardRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MovieAwardFileServiceTest {

    private MovieAwardFileService movieAwardFileService;
    private MovieAwardRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = mock(MovieAwardRepositoryImpl.class);
        movieAwardFileService = new MovieAwardFileService(repository);
    }

    @Test
    void shouldSaveDataWhenProcessValidCsvFile() {
        movieAwardFileService.createMovieAwards("test_movie_list.csv");

        verify(repository, times(1)).create(anyList());

        var argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(repository).create(argumentCaptor.capture());

        List<MovieAward> capturedAwards = argumentCaptor.getValue();
        assertEquals(3, capturedAwards.size());
    }

    @Test
    void shouldThrowExceptionWhenProcessInvalidCsvFile() {
        var invalidFile = "invalid.csv";
        assertThrows(RuntimeException.class, () -> {
            movieAwardFileService.createMovieAwards(invalidFile);
        });
    }
}