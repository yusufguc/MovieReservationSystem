package com.yusufguc.controller;

import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.model.enums.Genre;
import com.yusufguc.utils.RestPageableEntity;
import com.yusufguc.utils.RestPageableRequest;


public interface MovieController {

    public RootEntity<MovieResponse> addMovie(MovieRequest movieRequest);

    public  void deleteMovie(Long id);

    public RootEntity<MovieResponse> updateMovie(Long id, MovieRequest movieRequest);


    public RootEntity<MovieResponse> getMovieById(Long id);


//----------------PAGEABLE--------------------------
    public RootEntity<RestPageableEntity<MovieResponse>> getAllMovies(RestPageableRequest pageable);

    public RootEntity<RestPageableEntity<MovieResponse>> getAllMoviesByGenre(Genre genre, RestPageableRequest pageable);

}
