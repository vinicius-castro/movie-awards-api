package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.domain.entity.MovieAward;

public class CreateMovieAwardUseCase extends UseCase<MovieAward, MovieAward> {

    private final MovieAwardRepository repository;

    public CreateMovieAwardUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    public MovieAward execute(MovieAward movieAward) {
        getLogger().info("m=execute, action=creating movie award, entity={}", movieAward);
        repository.create(movieAward);
        return movieAward;
    }
}
