package com.movieawards.api.infra.batch;

import com.movieawards.api.domain.entity.MovieAward;
import com.movieawards.api.infra.repository.MovieAwardRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        var csvContent = """
                year,title,studios,producers,winner
                1980,Movie A,Studio X,Producer 1,true
                1981,Movie B,Studio Y,Producer 2,
                1982,Movie C,Studio Z,Producer 3,false""";
        var tempFile = createTempCsvFile(csvContent);

        movieAwardFileService.createMovieAwards(tempFile.toString());

        verify(repository, times(1)).create(anyList());

        var argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(repository).create(argumentCaptor.capture());

        List<MovieAward> capturedAwards = argumentCaptor.getValue();
        assertEquals(3, capturedAwards.size());
        assertTrue(capturedAwards.get(0).winner());
        assertFalse(capturedAwards.get(1).winner());
    }

    @Test
    void shouldThrowExceptionWhenProcessInvalidCsvFile() {
        var invalidFile = "invalid.csv";
        assertThrows(RuntimeException.class, () -> {
            movieAwardFileService.createMovieAwards(invalidFile);
        });
    }

    private Path createTempCsvFile(String content) {
        try {
            var tempFile = Files.createTempFile("test", ".csv");
            Files.writeString(tempFile, content);
            tempFile.toFile().deleteOnExit();
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}