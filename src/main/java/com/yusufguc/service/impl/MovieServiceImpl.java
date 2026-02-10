package com.yusufguc.service.impl;


import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.model.Movie;
import com.yusufguc.model.enums.Genre;
import com.yusufguc.repository.MovieRepository;
import com.yusufguc.service.MovieService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieResponse addMovie(MovieRequest movieRequest) {

        if (movieRepository.existsByTitleIgnoreCase(movieRequest.getTitle().trim())){
            throw new BaseException(new ErrorMessage(MessageType.MOVIE_ALREADY_EXISTS, movieRequest.getTitle()));
        }
        Movie movie = toMovie(movieRequest);
        Movie savedMovie = movieRepository.save(movie);

        return  toMovieResponse(savedMovie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_MOVIE, id.toString())));

        movieRepository.delete(movie);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(Long id, MovieRequest movieRequest) {
        Movie movie =movieRepository.findById(id)
                .orElseThrow(() ->new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_MOVIE,id.toString())));

        movie.setTitle(movieRequest.getTitle());
        movie.setDescription(movieRequest.getDescription());
        movie.setGenre(movieRequest.getGenre());
        movie.setDurationMinutes(movieRequest.getDurationMinutes());

        Movie savedMovie = movieRepository.save(movie);
        return toMovieResponse(savedMovie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        List<Movie> allMoviesDB = movieRepository.findAll();

        List<MovieResponse> movieResponseList=new ArrayList<>();
        for (Movie movie : allMoviesDB) {
            movieResponseList.add(toMovieResponse(movie));
        }
        return movieResponseList;
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie =movieRepository.findById(id)
                .orElseThrow(() ->new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_MOVIE,id.toString())));
        return toMovieResponse(movie);
    }

    @Override
    public List<MovieResponse> getAllMoviesByGenre(Genre genre) {

        if (genre == null) {
            return getAllMovies();
        }
        List<Movie> moviesByGenre = movieRepository.findByGenre(genre);

        List<MovieResponse> movieResponseList=new ArrayList<>();
        for (Movie movie : moviesByGenre) {
            movieResponseList.add(toMovieResponse(movie));
        }
        return movieResponseList;
    }


//----------------PAGEABLE--------------------------
    @Override
    public Page<MovieResponse> getAllMovies(Pageable pageable) {
        Page<Movie> moviesPage = movieRepository.findAll(pageable);

        return moviesPage.map(this::toMovieResponse);
    }

    @Override
    public Page<MovieResponse> getAllMoviesByGenre(Genre genre, Pageable pageable) {

        Page<Movie> moviesPage = movieRepository.findAllByGenre(genre, pageable);

        return moviesPage.map(this::toMovieResponse);
    }

    private Movie toMovie(MovieRequest request){
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setGenre(request.getGenre());
        return movie;
    }

    private MovieResponse toMovieResponse(Movie movie){
        MovieResponse response = new MovieResponse();
        response.setTitle(movie.getTitle());
        response.setDescription(movie.getDescription());
        response.setDurationMinutes(movie.getDurationMinutes());
        response.setGenre(movie.getGenre());
        return response;
    }
}

