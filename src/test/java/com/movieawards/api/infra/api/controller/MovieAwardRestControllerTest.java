package com.movieawards.api.infra.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieawards.api.domain.entity.AwardsInterval;
import com.movieawards.api.domain.entity.AwardsIntervalDetail;
import com.movieawards.api.domain.entity.MovieAward;
import com.movieawards.api.infra.repository.MovieAwardRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieAwardRestController.class)
public class MovieAwardRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MovieAwardRepositoryImpl repository;

    @Test
    void shouldCreateMovieAward() throws Exception {
        var newMovie = MovieAward.builder()
                .year(2000)
                .title("movie")
                .studios("studios")
                .producers("producer 1 and producer 2")
                .winner(true)
                .build();

        mockMvc.perform(post("/movie-award")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMovie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.year").value(2000))
                .andExpect(jsonPath("$.title").value("movie"))
                .andExpect(jsonPath("$.studios").value("studios"))
                .andExpect(jsonPath("$.producers").value("producer 1 and producer 2"))
                .andExpect(jsonPath("$.winner").value(true));
    }

    @Test
    void shouldReturnMovieAward() throws Exception {
        var movieAward = MovieAward.builder().year(2000).title("movie 1").studios("studios 1").producers("Producer 1").build();
        when(repository.getByCode(anyString())).thenReturn(movieAward);

        mockMvc.perform(get("/movie-award/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(movieAward)));
    }

    @Test
    void shouldReturnMovieAwardsList() throws Exception {
        var movieAwards = List.of(
                MovieAward.builder().year(2000).title("movie 1").studios("studios 1").producers("Producer 1").build(),
                MovieAward.builder().year(2000).title("movie 2").studios("studios 2").producers("Producer 2").build()
        );
        when(repository.list()).thenReturn(movieAwards);

        mockMvc.perform(get("/movie-award")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(movieAwards)));
    }

    @Test
    void shouldReturnAwardsInterval() throws Exception {
        var winners = List.of(
                MovieAward.builder().year(2000).producers("Producer 1").build(),
                MovieAward.builder().year(2001).producers("Producer 1").build(),
                MovieAward.builder().year(2000).producers("Producer 2").build(),
                MovieAward.builder().year(2023).producers("Producer 2").build()
        );

        when(repository.listWinners()).thenReturn(winners);

        var minDetail = new AwardsIntervalDetail("Producer 1", 1, 2000, 2001);
        var maxDetail = new AwardsIntervalDetail("Producer 2", 23, 2000, 2023);
        var awardsInterval = new AwardsInterval(List.of(minDetail), List.of(maxDetail));

        mockMvc.perform(get("/movie-award/awards-interval")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(awardsInterval)));
    }

    @Test
    void shouldDeleteMovieAward() throws Exception {
        mockMvc.perform(delete("/movie-award/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}