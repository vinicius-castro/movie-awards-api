package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.base.UseCase;
import org.apache.logging.log4j.util.Strings;

public class RemoveMovieAwardByCodeUseCase extends UseCase<String, Void> {

    private final MovieAwardRepository repository;

    public RemoveMovieAwardByCodeUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(String value) {
        if (Strings.isBlank(value)) {
            getLogger().info("m=execute, action=removing movie award, message=empty value");
            return null;
        }
        getLogger().info("m=execute, action=removing movie award, id={}", value);
        repository.remove(value);
        getLogger().info("m=execute, action=successfully removed movie award");
        return null;
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
    }
}
