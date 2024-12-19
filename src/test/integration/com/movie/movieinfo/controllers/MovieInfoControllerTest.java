package com.movie.movieinfo.controllers;


import com.movie.movieinfo.model.MovieInfo;
import com.movie.movieinfo.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MovieInfoControllerTest {
    private static final String MOVIE_INFO_URL = "/info/movie";
    @Autowired
    MovieInfoRepository movieInfoRepository;
    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp () {
        List<MovieInfo> movieInfos = List.of(new MovieInfo("1", "The Matrix", 1999, List.of("Keanu Reeves", "Carrie-Anne Moss"), LocalDate.parse("1999-10-01")), new MovieInfo("2", "Inception", 2010, List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"), LocalDate.parse("2010-06-02")), new MovieInfo("3", "The Dark Knight", 2008, List.of("Christian Bale", "Heath Ledger"), LocalDate.parse("2008-05-02")));
        movieInfoRepository.saveAll(movieInfos).blockLast();
    }

    @AfterEach
    void tearDown () {
        movieInfoRepository.deleteAll();
    }

    @Test
    void addMovieInfo () {
        //Given
        MovieInfo dummyMovieInfo = new MovieInfo(null, "Interstellar", 2014, List.of("Matthew McConaughey", "Anne Hathaway"), LocalDate.parse("2014-11-07"));
        webTestClient.post()
                .uri(MOVIE_INFO_URL)
                .bodyValue(dummyMovieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Interstellar")
                .jsonPath("$.year").isEqualTo("2014");


    }
    @Test
    void getMovieInfo() {
        // Given
        String movieId = "1";

        // When & Then
        webTestClient.get()
                .uri(MOVIE_INFO_URL + "/{id}", movieId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("The Matrix")
                .jsonPath("$.year").isEqualTo(1999);
    }

    @Test
    void updateMovieInfo () {
        //Given
        String movieId ="1";

        //When

        final MovieInfo movieInfo = MovieInfo.builder()
                .id(null).year(2001).name("New Movie").cast(List.of("Test"))
                .releaseDate(LocalDate.parse("2001-01-02")).build();

        webTestClient.put()
                .uri(MOVIE_INFO_URL+"/{id}",movieId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(m->{
                    final MovieInfo responseBody = m.getResponseBody();
                    Assertions.assertNotNull(responseBody.getId());

                });

        //Then
    }

    @Test
    void testGetAllMovieInfo () {
        // When & Then
        webTestClient.get()
                .uri(MOVIE_INFO_URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MovieInfo.class)
                .hasSize(3); // Expecting 3 movies in the response
    }

}