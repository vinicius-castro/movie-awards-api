package com.movieawards.api.infra.repository;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;
import com.movieawards.api.infra.repository.entity.MovieAwardEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieAwardRepositoryImpl implements MovieAwardRepository {

    private final MovieAwardEntityRepository entityRepository;

    public MovieAwardRepositoryImpl(MovieAwardEntityRepository movieAwardEntityRepository) {
        this.entityRepository = movieAwardEntityRepository;
    }

    @Override
    public void create(MovieAward movieAward) {
        var entity = new MovieAwardEntity(movieAward);
        entityRepository.save(entity);
    }

    @Override
    public void create(List<MovieAward> movieAwards) {
        if (movieAwards.isEmpty())
            return;

        var entityList = new ArrayList<MovieAwardEntity>();
        movieAwards.forEach(m -> entityList.add(new MovieAwardEntity(m)));
        entityRepository.saveAll(entityList);
    }

    @Override
    public void remove(Long id) {
        entityRepository.deleteById(id);
    }

    @Override
    public MovieAward get(Long id) {
        var entity = entityRepository.findById(id);

        if (entity.isPresent()) {
            return entity
                    .map(e -> MovieAward.builder()
                            .year(e.getYear())
                            .title(e.getTitle())
                            .studios(e.getStudios())
                            .producers(e.getProducers())
                            .winner(e.getWinner())
                            .build())
                    .get();
        }
        return null;
    }

    @Override
    public List<MovieAward> list() {
        return entityRepository
                .findAll()
                .stream()
                .map(e -> MovieAward.builder()
                                .year(e.getYear())
                                .title(e.getTitle())
                                .studios(e.getStudios())
                                .producers(e.getProducers())
                                .winner(e.getWinner())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
