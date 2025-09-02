package com.movieawards.api.adapter;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.CreateMovieAwardUseCase;
import com.movieawards.api.application.usecase.ListMovieAwardsUseCase;
import com.movieawards.api.application.usecase.SearchMovieAwardByIdUseCase;
import com.movieawards.api.application.usecase.UseCase;
import com.movieawards.api.application.usecase.UseCaseOperation;

public class UseCaseFactory {

    private final MovieAwardRepository repository;

    public UseCaseFactory(MovieAwardRepository repository) {
        this.repository = repository;
    }

    public <I, O> UseCase<I, O> getUseCase(UseCaseOperation operation) {
        return (UseCase<I, O>) switch (operation) {
            case CREATE -> new CreateMovieAwardUseCase(repository);
            case SEARCH_BY_ID -> new SearchMovieAwardByIdUseCase(repository);
            case LIST -> new ListMovieAwardsUseCase(repository);
        };
    }
}
