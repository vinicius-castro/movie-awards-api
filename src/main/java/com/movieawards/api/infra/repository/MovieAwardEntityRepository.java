package com.movieawards.api.infra.repository;

import com.movieawards.api.infra.repository.entity.MovieAwardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieAwardEntityRepository extends JpaRepository<MovieAwardEntity, Long> { }
