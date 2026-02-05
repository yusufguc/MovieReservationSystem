package com.yusufguc.service.impl;


import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.model.Movie;
import com.yusufguc.model.enums.Genre;
import com.yusufguc.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    // -------------------addMovie-----------------------
    @Test
    void shouldAddMovieSuccessfully() {

        MovieRequest request = new MovieRequest();
        request.setTitle("Interstellar");
        request.setDescription("Space");
        request.setGenre(Genre.valueOf("SCIFI"));
        request.setDurationMinutes(169);

        when(movieRepository.existsByTitleIgnoreCase(anyString()))
                .thenReturn(false);

        Movie savedMovie = new Movie();
        savedMovie.setTitle("Interstellar");
        savedMovie.setGenre(Genre.valueOf("SCIFI"));
        savedMovie.setDurationMinutes(169);

        when(movieRepository.save(any(Movie.class)))
                .thenReturn(savedMovie);
        MovieResponse response = movieService.addMovie(request);

        assertEquals("Interstellar", response.getTitle());
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    void shouldThrowExceptionWhenMovieAlreadyExists() {

        MovieRequest request = new MovieRequest();
        request.setTitle("Interstellar");

        when(movieRepository.existsByTitleIgnoreCase(anyString()))
                .thenReturn(true);

        assertThrows(BaseException.class, () -> {
            movieService.addMovie(request);
        });

        verify(movieRepository, never()).save(any());
    }
//-------------------deleteMovie-----------------------
@Test
void shouldDeleteMovie_WhenIdExists() {
    Long movieId = 1L;
    Movie mockMovie = new Movie();
    mockMovie.setId(movieId);

    when(movieRepository.findById(movieId)).thenReturn(Optional.of(mockMovie));

    movieService.deleteMovie(movieId);

    verify(movieRepository, times(1)).findById(movieId);
    verify(movieRepository, times(1)).delete(mockMovie);
}

    @Test
    void shouldThrowException_WhenMovieDoesNotExist() {
        Long movieId = 999L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        BaseException exception = assertThrows(BaseException.class, () -> {
            movieService.deleteMovie(movieId);
        });

        String expectedMessage = MessageType.THERE_IS_NO_MOVIE.getMessage() + " : " + movieId;
        assertEquals(expectedMessage, exception.getMessage());

        verify(movieRepository, never()).delete(any());
    }

    //-------------------updateMovie-----------------------

    @Test
    void shouldUpdateMovie_WhenIdExists() {
        // Given
        Long movieId = 1L;
        MovieRequest request = new MovieRequest("Inception New", "New Desc", 150, Genre.SCIFI);

        Movie existingMovie = new Movie();
        existingMovie.setId(movieId);
        existingMovie.setTitle("Old Title");

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        MovieResponse response = movieService.updateMovie(movieId, request);

        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDescription(), response.getDescription());

        verify(movieRepository, times(1)).findById(movieId);
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void shouldThrowException_WhenUpdateMovieIdDoesNotExist() {
        Long movieId = 999L;
        MovieRequest request = new MovieRequest("Title", "Desc", 150, Genre.SCIFI);

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(BaseException.class, () -> {
            movieService.updateMovie(movieId, request);
        });

        verify(movieRepository, never()).save(any());
    }




}
