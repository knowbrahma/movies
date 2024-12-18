package com.movie.movieinfo.repository;


import com.movie.movieinfo.model.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {
    @Autowired
    private MovieInfoRepository repository;

    @BeforeEach
    void setUp () {
        List<MovieInfo> movieInfos = List.of(
                new MovieInfo("1", "The Matrix", 1999, List.of("Keanu Reeves", "Carrie-Anne Moss"), LocalDate.parse("1999-10-01")),
                new MovieInfo("2", "Inception", 2010, List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"), LocalDate.parse("2010-06-02")),
                new MovieInfo("3", "The Dark Knight", 2008, List.of("Christian Bale", "Heath Ledger"), LocalDate.parse("2008-05-02")));
        repository.saveAll(movieInfos).blockLast();
    }

    @AfterEach
    void tearDown () {
        repository.deleteAll();
    }

    @Test
    void findAll () {
        //Given
        final Flux<MovieInfo> movieInfoFlux = repository.findAll().log();
        //When
        StepVerifier.create(movieInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
        //Then
    }

    @Test
    void findById () {
        //Given
        final String movieId = "1";
        //When
        Mono<MovieInfo> movieInfoMono = repository.findById(movieId);
        //Then
        StepVerifier.create(movieInfoMono)
                .assertNext(movieInfo -> assertEquals("The Matrix", movieInfo.getName()))
                .verifyComplete();
    }

    @Test
    void saveMovie () {
        //Given
        MovieInfo dummyMovieInfo = new MovieInfo("4", "Interstellar", 2014, List.of("Matthew McConaughey", "Anne Hathaway"), LocalDate.parse("2014-11-07"));
        //When
        Mono<MovieInfo> savedMovie = this.repository.save(dummyMovieInfo);
        //Then
        StepVerifier.create(savedMovie)
                .assertNext(movieInfo -> {
                    assertEquals("Interstellar", movieInfo.getName());
                })
                .verifyComplete();
    }

}
