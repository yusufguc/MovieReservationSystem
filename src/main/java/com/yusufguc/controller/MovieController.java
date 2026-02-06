package com.yusufguc.controller;

import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.model.enums.Genre;

import java.util.List;

public interface MovieController {

    public RootEntity<MovieResponse> addMovie(MovieRequest movieRequest);

    public  void deleteMovie(Long id);

    public RootEntity<MovieResponse> updateMovie(Long id, MovieRequest movieRequest);

    public RootEntity<List<MovieResponse>> getAllMovies();

    public RootEntity<MovieResponse> getMovieById(Long id);

    public RootEntity<List<MovieResponse>> getAllMoviesByGenre(Genre genre);



}
