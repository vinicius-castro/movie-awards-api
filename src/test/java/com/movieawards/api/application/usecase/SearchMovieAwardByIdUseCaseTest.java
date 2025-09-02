package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SearchMovieAwardByIdUseCaseTest {

    private MovieAwardRepository repository;
    private SearchMovieAwardByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(MovieAwardRepository.class);
        useCase = new SearchMovieAwardByIdUseCase(repository);
    }

    @Test
    void shouldReturnMovieAwardWhenSearchingByAValidId() {
        var movieAward = MovieAward.builder().year(100).build();

        when(repository.get(any())).thenReturn(movieAward);

        var result = useCase.execute(1L);
        assertEquals(movieAward, result);
    }
}