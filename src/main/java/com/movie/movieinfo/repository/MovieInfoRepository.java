package com.movie.movieinfo.repository;

import com.movie.movieinfo.model.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo,String> {
}
