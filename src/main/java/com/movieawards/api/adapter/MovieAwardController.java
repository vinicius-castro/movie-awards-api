package com.movieawards.api.adapter;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.UseCaseOperation;
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

    public MovieAward searchMovieAwardById(Long id) {
        return (MovieAward)getFactory().getUseCase(UseCaseOperation.SEARCH_BY_ID).execute(id);
    }

    public List<MovieAward> listMovieAwards() {
        return (List<MovieAward>)getFactory().getUseCase(UseCaseOperation.LIST).execute(null);
    }

    private UseCaseFactory getFactory() {
        return new UseCaseFactory(repository);
    }
}
