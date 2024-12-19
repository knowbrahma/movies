package com.movie.movieinfo.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;


@Validated
@Document
@Builder
public class MovieInfo {
    @Id
    private String id;
    @NotBlank(message = "movieInfo.name must be present")
    private String name;
    @NotNull
    @Positive(message = "movieInfo.year must be a Positive Value")
    private Integer year;
    @NotNull
    private List<@NotBlank(message = "movieInfo.cast must be present") String> cast;
    private LocalDate releaseDate;

    public String getId () {
        return id;
    }

    public void setId (final String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (final String name) {
        this.name = name;
    }

    public Integer getYear () {
        return year;
    }

    public void setYear (final Integer year) {
        this.year = year;
    }

    public List<String> getCast () {
        return cast;
    }

    public void setCast (final List<String> cast) {
        this.cast = cast;
    }

    public LocalDate getReleaseDate () {
        return releaseDate;
    }

    public void setReleaseDate (final LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieInfo () {
          }

    public MovieInfo (final String id, final String name, final Integer year, final List<@NotBlank(message = "movieInfo.cast must be present") String> cast, final LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.cast = cast;
        this.releaseDate = releaseDate;
    }
}