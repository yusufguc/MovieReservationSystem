package com.yusufguc.service;

import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.model.Movie;
import com.yusufguc.model.enums.Genre;

import java.util.List;

public interface MovieService {

    public  MovieResponse addMovie(MovieRequest movieRequest);

    public  void deleteMovie(Long id);

    public  MovieResponse updateMovie(Long id ,MovieRequest movieRequest);

    public List<MovieResponse> getAllMovies();

    public  MovieResponse getMovieById(Long id);

    public List<MovieResponse> getAllMoviesByGenre(Genre genre);

}
