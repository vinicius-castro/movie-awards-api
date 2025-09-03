package com.movieawards.api.application.usecase;

import com.movieawards.api.application.exception.MovieAwardNotFoundException;
import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SearchMovieAwardByCodeUseCaseTest {

    private MovieAwardRepository repository;
    private SearchMovieAwardByCodeUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(MovieAwardRepository.class);
        useCase = new SearchMovieAwardByCodeUseCase(repository);
    }

    @Test
    void shouldReturnMovieAwardWhenCodeIsValid() {
        var code = UUID.randomUUID().toString();
        var movieAward = MovieAward.builder().year(100).build();
        when(repository.getByCode(code)).thenReturn(movieAward);

        var result = useCase.execute(code);
        assertNotNull(result);
        assertEquals(movieAward.year(), result.year());
        assertEquals(movieAward.title(), result.title());
        assertEquals(movieAward.studios(), result.studios());
        assertEquals(movieAward.producers(), result.producers());
        assertEquals(movieAward.winner(), result.winner());
    }

    @Test
    void shouldThrowMovieAwardNotFoundException() {
        when(repository.getByCode("CODE_1")).thenReturn(null);
        assertThrows(MovieAwardNotFoundException.class, () -> useCase.execute(any()));
    }

}