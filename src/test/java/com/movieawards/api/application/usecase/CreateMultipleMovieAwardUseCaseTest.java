package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

class CreateMultipleMovieAwardUseCaseTest {

    private MovieAwardRepository repository;
    private CreateMultipleMovieAwardUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(MovieAwardRepository.class);
        useCase = new CreateMultipleMovieAwardUseCase(repository);
    }

    @Test
    void shouldCallRepositoryCreateWithGivenMovieAward() {
        var movieAwards = List.of(MovieAward.builder().build());
        assertDoesNotThrow(() -> useCase.execute(movieAwards));
        verify(repository, only()).create(anyList());
    }

    @Test
    void shouldNotCallRepositoryCreateWhenGivenEmptyList() {
        List<MovieAward> movieAwards = List.of();
        assertDoesNotThrow(() -> useCase.execute(movieAwards));
        verify(repository, never()).create(anyList());
    }
}