package com.yusufguc.controller;

import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.model.enums.Genre;
import com.yusufguc.utils.RestPageableEntity;
import com.yusufguc.utils.RestPageableRequest;

import java.util.List;

public interface MovieController {

    public RootEntity<MovieResponse> addMovie(MovieRequest movieRequest);

    public  void deleteMovie(Long id);

    public RootEntity<MovieResponse> updateMovie(Long id, MovieRequest movieRequest);

    //Pageable
    public RootEntity<List<MovieResponse>> getAllMovies();

    public RootEntity<MovieResponse> getMovieById(Long id);

    //Pageable
    public RootEntity<List<MovieResponse>> getAllMoviesByGenre(Genre genre);

//----------------PAGEABLE--------------------------
    public RootEntity<RestPageableEntity<MovieResponse>> getAllMovies(RestPageableRequest pageable);

    public RootEntity<RestPageableEntity<MovieResponse>> getAllMoviesByGenre(Genre genre, RestPageableRequest pageable);

}
