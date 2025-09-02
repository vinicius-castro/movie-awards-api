package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;

import java.util.List;

public class ListMovieAwardsUseCase extends UseCase<Void, List<MovieAward>> {

    private final MovieAwardRepository repository;

    public ListMovieAwardsUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MovieAward> execute(Void value) {
        getLogger().info("m=execute, action=searching movie award, id={}", value);
        return repository.list();
    }
}
