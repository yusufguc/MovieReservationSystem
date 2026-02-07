package com.yusufguc.service.impl;

import com.yusufguc.dto.request.ShowtimeRequest;
import com.yusufguc.dto.response.ShowtimeResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.model.Hall;
import com.yusufguc.model.Movie;
import com.yusufguc.model.Showtime;
import com.yusufguc.model.enums.Genre;
import com.yusufguc.repository.HallRepository;
import com.yusufguc.repository.MovieRepository;
import com.yusufguc.repository.ShowTimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowTimeServiceImplTest {

    @InjectMocks
    private ShowTimeServiceImpl showTimeService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private HallRepository hallRepository;

    @Mock
    private ShowTimeRepository showTimeRepository;

    private Movie movie;
    private Hall hall;
    private ShowtimeRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Interstellar");
        movie.setDurationMinutes(169);

        hall = new Hall();
        hall.setId(1L);
        hall.setName("HALL_1");

        request = new ShowtimeRequest();
        request.setHallId(hall.getId());
        request.setStartTime(LocalDateTime.now().plusDays(1));
    }

    @Test
    void addShowTime_whenMovieNotFound_thenThrowException() {
        ShowtimeRequest request = new ShowtimeRequest();
        request.setHallId(1L);
        request.setStartTime(LocalDateTime.now().plusDays(1));

        BaseException exception = assertThrows(BaseException.class, () ->
                showTimeService.addShowTime(999L, request)); // 999L yok varsayalım

        String expectedMessage = MessageType.THERE_IS_NO_MOVIE.getMessage() + " : 999";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void addShowTime_whenHallNotFound_thenThrowException() {
        // given
        ShowtimeRequest request = new ShowtimeRequest();
        request.setHallId(999L); // yok
        request.setStartTime(LocalDateTime.now().plusDays(1));

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Interstellar");
        movie.setDescription("Space movie");
        movie.setGenre(Genre.SCIFI);
        movie.setDurationMinutes(169);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        BaseException exception = assertThrows(BaseException.class, () ->
                showTimeService.addShowTime(1L, request));

        String expectedMessage = MessageType.THERE_IS_NO_HALL.getMessage() + " : 999";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void addShowTime_success() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(hallRepository.findById(hall.getId())).thenReturn(Optional.of(hall));
        when(showTimeRepository.findByHallId(hall.getId())).thenReturn(java.util.Collections.emptyList());
        when(showTimeRepository.save(any(Showtime.class))).thenAnswer(i -> i.getArguments()[0]);

        var response = showTimeService.addShowTime(1L, request);

        assertNotNull(response);
        assertEquals(movie.getTitle(), response.getMovie().getTitle());
        assertEquals(hall.getName(), response.getHallName());
    }


    @Test
    void updateShowTime_whenMovieOrHallNotFound_thenThrowException() {
        ShowtimeRequest request = new ShowtimeRequest();
        request.setHallId(1L);
        request.setStartTime(LocalDateTime.now().plusDays(1));

        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        BaseException movieException = assertThrows(BaseException.class, () ->
                showTimeService.updateShowTime(999L, 1L, request));
        String expectedMovieMessage = MessageType.THERE_IS_NO_MOVIE.getMessage() + " : 999";
        assertTrue(movieException.getMessage().contains(expectedMovieMessage));

        Movie movieDb = new Movie();
        movieDb.setId(1L);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieDb));
        when(showTimeRepository.findById(1L)).thenReturn(Optional.of(new Showtime()));

        BaseException hallException = assertThrows(BaseException.class, () ->
                showTimeService.updateShowTime(1L, 1L, request));
        String expectedHallMessage = MessageType.THERE_IS_NO_HALL.getMessage() + " : 1";
        assertTrue(hallException.getMessage().contains(expectedHallMessage));
    }


    @Test
    void getShowTimeByDate_shouldReturnShowtimesForGivenDate() {
        LocalDate date = LocalDate.now();
        Showtime showtime = new Showtime();
        showtime.setStartTime(date.atTime(18, 0));
        showtime.setEndTime(date.atTime(20, 30));
        showtime.setMovie(new Movie());
        showtime.setHall(new Hall());

        when(showTimeRepository.findByStartTimeBetween(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(showtime));

        List<ShowtimeResponse> result = showTimeService.getShowTimeByDate(date);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(showtime.getStartTime(), result.get(0).getStartTime());
    }




}
