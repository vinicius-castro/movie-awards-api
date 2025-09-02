package com.movieawards.api.domain.entity;

import java.util.List;

public record AwardsInterval(List<AwardsIntervalDetail> min, List<AwardsIntervalDetail> max) { }


