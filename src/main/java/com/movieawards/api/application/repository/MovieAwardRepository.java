package com.movieawards.api.application.repository;

import com.movieawards.api.domain.entity.MovieAward;

import java.util.List;

public interface MovieAwardRepository {

    void create(MovieAward movieAward);
    void create(List<MovieAward> movieAward);
    void remove(Long id);
    MovieAward get(Long id);
    List<MovieAward> list();
}
