package com.movieawards.api.infra.startup;

import com.movieawards.api.infra.batch.MovieAwardFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final MovieAwardFileService movieAwardFileService;

    public static final String PATH_CSV = "data/movielist.csv";

    public Runner(MovieAwardFileService movieAwardFileService) {
        this.movieAwardFileService = movieAwardFileService;
    }

    @Override
    public void run(String... args) throws Exception {
        movieAwardFileService.createMovieAwards(PATH_CSV);
    }
}
