package com.movieawards.api.application.repository;

import com.movieawards.api.domain.entity.MovieAward;

import java.util.List;

public interface MovieAwardRepository {

    void create(MovieAward movieAward);
    void create(List<MovieAward> movieAward);
    void remove(String code);
    MovieAward getByCode(String code);
    List<MovieAward> list();
}
