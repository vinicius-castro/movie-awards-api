package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.base.UseCase;
import com.movieawards.api.domain.entity.MovieAward;
import com.movieawards.api.application.exception.MovieAwardNotFoundException;

import java.util.Objects;

public class SearchMovieAwardByCodeUseCase extends UseCase<String, MovieAward> {

    private final MovieAwardRepository repository;

    public SearchMovieAwardByCodeUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public MovieAward execute(String value) {
        getLogger().info("m=execute, action=searching movie award, id={}", value);
        var result = repository.getByCode(value);
        if (Objects.isNull(result))
            throw new MovieAwardNotFoundException();
        return result;
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
    }
}
