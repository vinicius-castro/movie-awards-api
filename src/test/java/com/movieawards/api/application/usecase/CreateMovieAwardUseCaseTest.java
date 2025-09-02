package com.movieawards.api.application.usecase;


import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

class CreateMovieAwardUseCaseTest {

    private MovieAwardRepository repository;
    private CreateMovieAwardUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(MovieAwardRepository.class);
        useCase = new CreateMovieAwardUseCase(repository);
    }

    @Test
    void shouldCallRepositoryCreateWithGivenMovieAward() {
        var movieAward = MovieAward.builder().build();
        assertDoesNotThrow(() -> {
            var result = useCase.execute(movieAward);
            assertEquals(movieAward, result);
        });
        verify(repository, only()).create(movieAward);
    }
}