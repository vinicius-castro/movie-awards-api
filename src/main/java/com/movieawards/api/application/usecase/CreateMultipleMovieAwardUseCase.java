package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.base.UseCase;
import com.movieawards.api.domain.entity.MovieAward;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateMultipleMovieAwardUseCase extends UseCase<List<MovieAward>, Void> {

    private final MovieAwardRepository repository;

    public CreateMultipleMovieAwardUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(List<MovieAward> value) {
        if (value.isEmpty()){
            getLogger().info("m=execute, action=creating movie award by a list, message=list is empty");
            return null;
        }

        getLogger().info("m=execute, action=creating movie award by a list, quantity={}", value.size());
        var movieAwards = new ArrayList<MovieAward>();
        value.parallelStream().forEach(input -> movieAwards.add(
                MovieAward.builder()
                        .code(UUID.randomUUID().toString())
                        .year(input.year())
                        .title(input.title())
                        .studios(input.studios())
                        .producers(input.producers())
                        .winner(input.winner())
                        .build()
        ));
        repository.create(movieAwards);
        getLogger().info("m=execute, action=successfully created movie awards by a list");
        return null;
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
    }
}
