package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.base.UseCase;
import com.movieawards.api.domain.entity.MovieAward;

import java.util.UUID;

public class CreateMovieAwardUseCase extends UseCase<MovieAward, MovieAward> {

    private final MovieAwardRepository repository;

    public CreateMovieAwardUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public MovieAward execute(MovieAward input) {
        getLogger().info("m=execute, action=creating movie award, entity={}", input);
        var movieAward = MovieAward.builder()
                .code(UUID.randomUUID().toString())
                .year(input.year())
                .title(input.title())
                .studios(input.studios())
                .producers(input.producers())
                .winner(input.winner())
                .build();
        repository.create(movieAward);
        getLogger().info("m=execute, action=successfully created movie award");
        return movieAward;
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
    }
}
