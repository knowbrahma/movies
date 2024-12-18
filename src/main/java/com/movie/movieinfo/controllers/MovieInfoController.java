package com.movie.movieinfo.controllers;

import com.movie.movieinfo.model.MovieInfo;
import com.movie.movieinfo.services.MovieInfoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/info")
public class MovieInfoController {

    private final MovieInfoService movieInfoService;

    public MovieInfoController (final MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @PostMapping
    public Mono<MovieInfo> createMovieInfo (@RequestBody MovieInfo movieInfo) {
        return movieInfoService.save(movieInfo);
    }

    @GetMapping("/{id}")
    public Mono<MovieInfo> getMovieInfo (@PathVariable String id) {
        return movieInfoService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<MovieInfo> updateMovieInfo (@PathVariable String id, @RequestBody MovieInfo movieInfo) {
        return movieInfoService.update(id, movieInfo);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteMovieInfo (@PathVariable String id) {
        return movieInfoService.deleteById(id);
    }


}
