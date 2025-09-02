package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

class RemoveMovieAwardByCodeUseCaseTest {
    private MovieAwardRepository repository;
    private RemoveMovieAwardByCodeUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(MovieAwardRepository.class);
        useCase = new RemoveMovieAwardByCodeUseCase(repository);
    }

    @Test
    void shouldCallRepositoryRemoveWithGivenCode() {
        var code = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> useCase.execute(code));
        verify(repository, only()).remove(any());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "" })
    void shouldNotCallRepositoryRemoveWhenGivenEmptyCode(String code) {
        assertDoesNotThrow(() -> useCase.execute(code));
        verify(repository, never()).remove(any());
    }
}