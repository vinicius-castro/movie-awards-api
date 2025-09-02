package com.movieawards.api.adapter;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.CreateMovieAwardUseCase;
import com.movieawards.api.application.usecase.CreateMultipleMovieAwardUseCase;
import com.movieawards.api.application.usecase.ListMovieAwardsUseCase;
import com.movieawards.api.application.usecase.RemoveMovieAwardByCodeUseCase;
import com.movieawards.api.application.usecase.SearchMovieAwardByCodeUseCase;
import com.movieawards.api.application.usecase.base.UseCase;
import com.movieawards.api.application.usecase.enums.UseCaseOperation;

public record UseCaseFactory(MovieAwardRepository repository) {

    public <I, O> UseCase<I, O> getUseCase(UseCaseOperation operation) {
        return (UseCase<I, O>) switch (operation) {
            case CREATE -> new CreateMovieAwardUseCase(repository);
            case CREATE_MULTIPLES -> new CreateMultipleMovieAwardUseCase(repository);
            case LIST -> new ListMovieAwardsUseCase(repository);
            case REMOVE_BY_CODE -> new RemoveMovieAwardByCodeUseCase(repository);
            case SEARCH_BY_CODE -> new SearchMovieAwardByCodeUseCase(repository);
        };
    }
}
