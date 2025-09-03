package com.movieawards.api.domain.entity;

public record MovieAward(
        String code,
        Integer year,
        String title,
        String studios,
        String producers,
        Boolean winner
) {
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String code;
        private Integer year;
        private String title;
        private String studios;
        private String producers;
        private Boolean winner;

        public Builder() {}

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder year(Integer year) {
            this.year = year;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder studios(String studios) {
            this.studios = studios;
            return this;
        }

        public Builder producers(String producers) {
            this.producers = producers;
            return this;
        }

        public Builder winner(Boolean winner) {
            this.winner = winner;
            return this;
        }

        public MovieAward build() {
            return new MovieAward(code, year, title, studios, producers, winner);
        }
    }
}


