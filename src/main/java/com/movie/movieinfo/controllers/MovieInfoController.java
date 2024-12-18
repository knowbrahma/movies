package com.movie.movieinfo.controllers;

import com.movie.movieinfo.services.MovieInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/info")
public class MovieInfoController {

    public MovieInfoController (final MovieInfoService movieInfoService) {
    }

    @GetMapping("/test")
    public Mono<String> getTestMessage() {
        return Mono.just("This is a test message");
    }
    
    @GetMapping("/movie/{id}")
    public Mono<String> findMovieInfoById(String id) {
        return Mono.just("Placeholder: Get movie info by id");
    }
    
    @GetMapping("/movies")
    public Mono<String> findAllMovies() {
        return Mono.just("Placeholder: Get all movies");
    }
    
    @GetMapping("/create")
    public Mono<String> createMovieInfo() {
        return Mono.just("Placeholder: Create movie info");
    }
    
    @GetMapping("/update/{id}")
    public Mono<String> updateMovieInfo(String id) {
        return Mono.just("Placeholder: Update movie info");
    }
    
    
}
