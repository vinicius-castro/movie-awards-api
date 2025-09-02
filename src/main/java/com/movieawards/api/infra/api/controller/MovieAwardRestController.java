package com.movieawards.api.infra.api.controller;

import com.movieawards.api.adapter.MovieAwardController;
import com.movieawards.api.domain.entity.MovieAward;
import com.movieawards.api.infra.repository.MovieAwardRepositoryImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie-award")
public class MovieAwardRestController {

    private final MovieAwardRepositoryImpl repository;

    public MovieAwardRestController(MovieAwardRepositoryImpl repository) {
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity<List<MovieAward>> list() {
        return ResponseEntity.ok(new MovieAwardController(repository).listMovieAwards());
    }

    @GetMapping("/{code}")
    public ResponseEntity<MovieAward> get(@PathVariable String code) {
        return ResponseEntity.ok(new MovieAwardController(repository).searchMovieAwardByCode(code));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        new MovieAwardController(repository).removeMovieAwardByCode(code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MovieAward> create(@RequestBody MovieAward movieAward) {
        return ResponseEntity.ok(new MovieAwardController(repository).createMovieAward(movieAward));
    }
}
