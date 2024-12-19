package com.movie.movieinfo.controllers;

import com.movie.movieinfo.model.MovieInfo;
import com.movie.movieinfo.services.MovieInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/info")
public class MovieInfoController {

    private final MovieInfoService movieInfoService;

    public MovieInfoController (final MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @PostMapping("/movie")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> createMovieInfo (@RequestBody MovieInfo movieInfo) {
        return movieInfoService.save(movieInfo);
    }

    @GetMapping("/movie")
    public Flux<MovieInfo> getAllMovies() {
        return movieInfoService.findAll();
    }

    @GetMapping("/movie/{id}")
    public Mono<MovieInfo> getMovieInfo (@PathVariable String id) {
        return movieInfoService.findById(id);
    }

    @PutMapping("/movie/{id}")
    public Mono<MovieInfo> updateMovieInfo (@PathVariable String id, @RequestBody MovieInfo movieInfo) {
        return movieInfoService.update(id, movieInfo);
    }

    @DeleteMapping("/movie/{id}")
    public Mono<Void> deleteMovieInfo (@PathVariable String id) {
        return movieInfoService.deleteById(id);
    }


}
