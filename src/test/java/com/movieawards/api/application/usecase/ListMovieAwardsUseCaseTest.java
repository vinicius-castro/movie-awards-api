package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


class ListMovieAwardsUseCaseTest {

    private MovieAwardRepository repository;
    private ListMovieAwardsUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(MovieAwardRepository.class);
        useCase = new ListMovieAwardsUseCase(repository);
    }

    @Test
    void shouldListMovieAwards() {
        var movieAwards = new ArrayList<MovieAward>();
        movieAwards.add(MovieAward.builder().year(100).build());
        movieAwards.add(MovieAward.builder().year(200).build());

        when(repository.list()).thenReturn(movieAwards);

        var result = useCase.execute(null);
        assertTrue(result.containsAll(movieAwards));
    }

}