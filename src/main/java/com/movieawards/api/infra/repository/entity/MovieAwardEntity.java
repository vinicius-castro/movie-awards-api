package com.movieawards.api.infra.repository.entity;

import com.movieawards.api.domain.entity.MovieAward;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_award")
@Access(AccessType.FIELD)
public class MovieAwardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "award_year")
    private Integer year;
    private String code;
    private String title;
    private String studios;
    private String producers;
    private Boolean winner;

    protected MovieAwardEntity() {}

    public MovieAwardEntity(String code, Integer year, String title, String studios, String producers, Boolean winner) {
        this.code = code;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public MovieAwardEntity(MovieAward movieAward) {
        this.code = movieAward.code();
        this.year = movieAward.year();
        this.title = movieAward.title();
        this.studios = movieAward.studios();
        this.producers = movieAward.producers();
        this.winner = movieAward.winner();
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public Integer getYear() { return year; }
    public String getTitle() { return title; }
    public String getStudios() { return studios; }
    public String getProducers() { return producers; }
    public Boolean getWinner() { return winner; }
}