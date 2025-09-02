package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.base.UseCase;
import com.movieawards.api.domain.entity.AwardsInterval;
import com.movieawards.api.domain.entity.AwardsIntervalDetail;

import java.util.List;

public class SearchAwardsIntervalUseCase extends UseCase<Void, AwardsInterval> {

    private final MovieAwardRepository repository;

    public SearchAwardsIntervalUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public AwardsInterval execute(Void value) {
        var min = new AwardsIntervalDetail("producer new", 1, 2008, 2009);
        var max = new AwardsIntervalDetail("producer old", 99, 1900, 1999);
        return new AwardsInterval(List.of(min), List.of(max));
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
    }
}
