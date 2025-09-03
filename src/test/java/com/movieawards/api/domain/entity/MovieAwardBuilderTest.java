package com.movieawards.api.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MovieAwardBuilderTest {

    @Test
    void shouldBuildMovieAwardWithAllFields() {
        MovieAward award = new MovieAward.Builder()
                .year(2023)
                .title("Oppenheimer")
                .studios("Universal Pictures")
                .producers("Emma Thomas, Charles Roven, Christopher Nolan")
                .winner(true)
                .build();

        assertEquals(2023, award.year());
        assertEquals("Oppenheimer", award.title());
        assertEquals("Universal Pictures", award.studios());
        assertEquals("Emma Thomas, Charles Roven, Christopher Nolan", award.producers());
        assertTrue(award.winner());
    }

    @Test
    void shouldAllowPartialBuild() {
        MovieAward award = new MovieAward.Builder()
                .title("Matrix")
                .winner(false)
                .build();

        assertNull(award.year());
        assertEquals("Matrix", award.title());
        assertNull(award.studios());
        assertNull(award.producers());
        assertFalse(award.winner());
    }

}