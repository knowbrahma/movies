package com.movie.movieinfo.controllers;

import com.movie.movieinfo.model.MovieInfo;
import com.movie.movieinfo.services.MovieInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;


@WebFluxTest(controllers = MovieInfoController.class)
@AutoConfigureWebTestClient
class MovieControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private MovieInfoService movieInfoService;

    private static final String MOVIE_INFO_URL = "/info/movie";

    @Test
    void getAllMovieInfo () {
        List<MovieInfo> movieInfos = List.of(new MovieInfo("1", "The Matrix", 1999, List.of("Keanu Reeves", "Carrie-Anne Moss"), LocalDate.parse("1999-10-01")), new MovieInfo("2", "Inception", 2010, List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"), LocalDate.parse("2010-06-02")), new MovieInfo("3", "The Dark Knight", 2008, List.of("Christian Bale", "Heath Ledger"), LocalDate.parse("2008-05-02")));

        when(movieInfoService.findAll()).thenReturn(Flux.fromIterable(movieInfos));

        webTestClient.get().uri(MOVIE_INFO_URL).exchange().expectStatus().is2xxSuccessful().expectBodyList(MovieInfo.class).hasSize(3);
    }

    @Test
    void addMovieInfo () {
        //Given
        final MovieInfo movieInfo = new MovieInfo("1", "The Matrix", 1999, List.of("Keanu Reeves", "Carrie-Anne Moss"), LocalDate.parse("1999-10-01"));
        when(movieInfoService.save(isA(MovieInfo.class))).thenReturn(Mono.just(movieInfo));
        //When
        webTestClient.post()
                .uri(MOVIE_INFO_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(m->{
                    final MovieInfo body = m.getResponseBody();
                    Assertions.assertNotNull(body);
                    Assertions.assertEquals("The Matrix",body.getName());
                });
        //Then
    }
}
