package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;

public class SearchMovieAwardByIdUseCase extends UseCase<Long, MovieAward> {

    private final MovieAwardRepository repository;

    public SearchMovieAwardByIdUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public MovieAward execute(Long value) {
        getLogger().info("m=execute, action=searching movie award, id={}", value);
        return repository.get(value);
    }
}
