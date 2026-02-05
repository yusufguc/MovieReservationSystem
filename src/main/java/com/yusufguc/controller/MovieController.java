package com.yusufguc.controller;

import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.model.RootEntity;

public interface MovieController {

    public RootEntity<MovieResponse> addMovie(MovieRequest movieRequest);

    public  void deleteMovie(Long id);

    public RootEntity<MovieResponse> updateMovie(Long id, MovieRequest movieRequest);


}
