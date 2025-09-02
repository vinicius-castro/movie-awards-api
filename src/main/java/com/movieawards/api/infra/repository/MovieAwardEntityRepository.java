package com.movieawards.api.infra.repository;

import com.movieawards.api.infra.repository.entity.MovieAwardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieAwardEntityRepository extends JpaRepository<MovieAwardEntity, Long> {

    Optional<MovieAwardEntity> findByCode(String code);

    List<MovieAwardEntity> findByWinnerTrue();

    @Transactional
    void deleteByCode(String code);
}
