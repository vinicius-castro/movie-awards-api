package com.movieawards.api.domain.entity;

public record AwardsIntervalDetail(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer followingWin
) { }
