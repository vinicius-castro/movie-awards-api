package com.movieawards.api.application.usecase;

import com.movieawards.api.application.exception.MovieAwardNotFoundException;
import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.base.UseCase;
import com.movieawards.api.domain.entity.MovieAward;

import java.util.List;
import java.util.Objects;

public class ListMovieAwardsUseCase extends UseCase<Void, List<MovieAward>> {

    private final MovieAwardRepository repository;

    public ListMovieAwardsUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MovieAward> execute(Void value) {
        getLogger().info("m=execute, action=searching movie awards");
        var result = repository.list();
        if (Objects.isNull(result) || result.isEmpty())
            throw new MovieAwardNotFoundException();
        return result;
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
    }
}
