package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchAwardsIntervalUseCaseTest {

    private MovieAwardRepository repository;
    private SearchAwardsIntervalUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(MovieAwardRepository.class);
        useCase = new SearchAwardsIntervalUseCase(repository);
    }

    @Test
    void shouldReturnCorrectIntervalsWhenMultipleAwardsExist() {
        when(repository.listWinners()).thenReturn(List.of(
                MovieAward.builder().year(2000).producers("John and Maria").build(),
                MovieAward.builder().year(2002).producers("John and Maria").build(),
                MovieAward.builder().year(2000).producers("Miguel").build(),
                MovieAward.builder().year(2008).producers("Miguel").build()
        ));

        var result = useCase.execute(null);

        assertNotNull(result);

        var minIntervals = result.min();
        assertNotNull(minIntervals);
        assertEquals(2, minIntervals.size());
        assertEquals("John", minIntervals.getFirst().producer());
        assertEquals(2, minIntervals.getFirst().interval());
        assertEquals(2000, minIntervals.getFirst().previousWin());
        assertEquals(2002, minIntervals.getFirst().followingWin());

        var maxIntervals = result.max();
        assertNotNull(maxIntervals);
        assertEquals(1, maxIntervals.size());
        assertEquals("Miguel", maxIntervals.getFirst().producer());
        assertEquals(8, maxIntervals.getFirst().interval());
        assertEquals(2000, maxIntervals.getFirst().previousWin());
        assertEquals(2008, maxIntervals.getFirst().followingWin());
    }

    @Test
    void shouldReturnNullWhenNoWinners() {
        when(repository.listWinners()).thenReturn(Collections.emptyList());
        var result = useCase.execute(null);
        assertNull(result);
    }
}