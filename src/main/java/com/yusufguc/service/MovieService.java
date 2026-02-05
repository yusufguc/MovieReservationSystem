package com.yusufguc.service;

import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;

public interface MovieService {

    public  MovieResponse addMovie(MovieRequest movieRequest);

    public  void deleteMovie(Long id);

    public  MovieResponse updateMovie(Long id ,MovieRequest movieRequest);

}
