package com.movie.movieinfo.services;

import com.movie.movieinfo.model.MovieInfo;
import com.movie.movieinfo.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {
    private MovieInfoRepository repository;

    public MovieInfoService (final MovieInfoRepository repository) {
        this.repository = repository;
    }

    public Flux<MovieInfo> findAll() {
        return repository.findAll();
    }

    public Mono<MovieInfo> findById(String id) {
        return repository.findById(id);
    }

    public Mono<MovieInfo> save(MovieInfo movieInfo) {
        return repository.save(movieInfo);
    }

    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
    public Mono<MovieInfo> update(String id, MovieInfo movieInfo) {
        return repository.findById(id)
                .flatMap(existingMovieInfo -> {
                    movieInfo.setId(existingMovieInfo.getId());
                    movieInfo.setReleaseDate(existingMovieInfo.getReleaseDate());
                    movieInfo.setCast(existingMovieInfo.getCast());
                    movieInfo.setName(existingMovieInfo.getName());
                    movieInfo.setYear(existingMovieInfo.getYear());

                    return repository.save(movieInfo);
                });
    }
    
    
    
}
