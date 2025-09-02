package com.movieawards.api.infra.repository;

import com.movieawards.api.domain.entity.MovieAward;
import com.movieawards.api.infra.repository.entity.MovieAwardEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieAwardRepositoryImplTest {

    private MovieAwardEntityRepository entityRepository;
    private MovieAwardRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        entityRepository = mock(MovieAwardEntityRepository.class);
        repository = new MovieAwardRepositoryImpl(entityRepository);
    }

    @Test
    void shouldSaveOnEntityRepository() {
        var movieAward = MovieAward.builder()
                .year(2023)
                .title("Oppenheimer")
                .studios("Universal")
                .producers("Nolan")
                .winner(true)
                .build();

        repository.create(movieAward);

        verify(entityRepository, times(1)).save(any(MovieAwardEntity.class));
    }

    @Test
    void shouldDeleteByIdOnEntityRepository() {
        var id = 1L;

        repository.remove(id);

        verify(entityRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldReturnMovieAwardWhenExistsInDatabase() {
        var id = 1L;
        var entity = new MovieAwardEntity(2023, "Oppenheimer", "Universal", "Nolan", true);

        when(entityRepository.findById(id)).thenReturn(Optional.of(entity));

        var result = repository.get(id);

        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Oppenheimer");
        assertThat(result.year()).isEqualTo(2023);
        assertThat(result.studios()).isEqualTo("Universal");
        assertThat(result.producers()).isEqualTo("Nolan");
        assertThat(result.winner()).isTrue();
    }

    @Test
    void shouldReturnNullWhenDoesNotExistInDatabase() {
        var id = 1L;

        when(entityRepository.findById(id)).thenReturn(Optional.empty());

        var result = repository.get(id);

        assertThat(result).isNull();
    }

    @Test
    void shouldListMappedMovieAwards() {
        var entity1 = new MovieAwardEntity(2023, "Oppenheimer", "Universal", "Nolan", true);
        var entity2 = new MovieAwardEntity(1994, "Forrest Gump", "Paramount", "Tom Hanks", true);

        when(entityRepository.findAll()).thenReturn(List.of(entity1, entity2));

        var results = repository.list();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).title()).isEqualTo("Oppenheimer");
        assertThat(results.get(1).title()).isEqualTo("Forrest Gump");
    }
}
