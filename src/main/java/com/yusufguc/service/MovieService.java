package com.yusufguc.service;

import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.model.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MovieService {

    public  MovieResponse addMovie(MovieRequest movieRequest);

    public  void deleteMovie(Long id);

    public  MovieResponse updateMovie(Long id ,MovieRequest movieRequest);

    //public List<MovieResponse> getAllMovies();

    public  MovieResponse getMovieById(Long id);

//    public List<MovieResponse> getAllMoviesByGenre(Genre genre);

    //-------------PAGEABLE--------------
    public Page<MovieResponse> getAllMovies(Pageable pageable) ;
    public Page<MovieResponse> getAllMoviesByGenre(Genre genre, Pageable pageable);

    }
