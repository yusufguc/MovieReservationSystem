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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieResponse addMovie(MovieRequest movieRequest) {

        if (movieRepository.existsByTitleIgnoreCase(movieRequest.getTitle().trim())){
            throw new BaseException(new ErrorMessage(MessageType.MOVIE_ALREADY_EXISTS, movieRequest.getTitle()));
        }
        Movie movie=new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setDescription(movieRequest.getDescription());
        movie.setGenre(movieRequest.getGenre());
        movie.setDurationMinutes(movieRequest.getDurationMinutes());

        Movie savedMovie = movieRepository.save(movie);

        MovieResponse movieResponse=new MovieResponse();
        movieResponse.setTitle(savedMovie.getTitle());
        movieResponse.setDescription(savedMovie.getDescription());
        movieResponse.setDurationMinutes(savedMovie.getDurationMinutes());
        movieResponse.setGenre(savedMovie.getGenre());

        return  movieResponse;
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

        MovieResponse movieResponse=new MovieResponse();
        movieResponse.setTitle(savedMovie.getTitle());
        movieResponse.setDescription(savedMovie.getDescription());
        movieResponse.setGenre(savedMovie.getGenre());
        movieResponse.setDurationMinutes(savedMovie.getDurationMinutes());

        return movieResponse;
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        List<Movie> allMoviesDB = movieRepository.findAll();

        List<MovieResponse> movieResponseList=new ArrayList<>();
        for (Movie movie : allMoviesDB) {
            MovieResponse movieResponse = new MovieResponse();
            movieResponse.setTitle(movie.getTitle());
            movieResponse.setDescription(movie.getDescription());
            movieResponse.setGenre(movie.getGenre());
            movieResponse.setDurationMinutes(movie.getDurationMinutes());

            movieResponseList.add(movieResponse);
        }
        return movieResponseList;
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie =movieRepository.findById(id)
                .orElseThrow(() ->new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_MOVIE,id.toString())));

        MovieResponse movieResponse=new MovieResponse();
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setDescription(movie.getDescription());
        movieResponse.setGenre(movie.getGenre());
        movieResponse.setDurationMinutes(movie.getDurationMinutes());
        return movieResponse;
    }

    @Override
    public List<MovieResponse> getAllMoviesByGenre(Genre genre) {

        if (genre == null) {
            return getAllMovies();
        }
        List<Movie> moviesByGenre = movieRepository.findByGenre(genre);

        List<MovieResponse> movieResponseList=new ArrayList<>();
        for (Movie movie : moviesByGenre) {
            MovieResponse movieResponse = new MovieResponse();
            movieResponse.setTitle(movie.getTitle());
            movieResponse.setDescription(movie.getDescription());
            movieResponse.setGenre(movie.getGenre());
            movieResponse.setDurationMinutes(movie.getDurationMinutes());

            movieResponseList.add(movieResponse);
        }
        return movieResponseList;
    }


}

