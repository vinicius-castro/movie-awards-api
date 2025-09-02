package com.movieawards.api.adapter;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.enums.UseCaseOperation;
import com.movieawards.api.domain.entity.MovieAward;

import java.util.List;

public class MovieAwardController {

    private final MovieAwardRepository repository;

    public MovieAwardController(MovieAwardRepository repository) {
        this.repository = repository;
    }

    public MovieAward createMovieAward(MovieAward movieAward) {
        return (MovieAward)getFactory().getUseCase(UseCaseOperation.CREATE).execute(movieAward);
    }

    public void createMovieAward(List<MovieAward> movieAwards) {
        getFactory().getUseCase(UseCaseOperation.CREATE_MULTIPLES).execute(movieAwards);
    }

    public List<MovieAward> listMovieAwards() {
        return (List<MovieAward>)getFactory().getUseCase(UseCaseOperation.LIST).execute(null);
    }

    public void removeMovieAwardByCode(String code) {
        getFactory().getUseCase(UseCaseOperation.REMOVE_BY_CODE).execute(code);
    }

    public MovieAward searchMovieAwardByCode(String code) {
        return (MovieAward)getFactory().getUseCase(UseCaseOperation.SEARCH_BY_CODE).execute(code);
    }

    private UseCaseFactory getFactory() {
        return new UseCaseFactory(repository);
    }
}
