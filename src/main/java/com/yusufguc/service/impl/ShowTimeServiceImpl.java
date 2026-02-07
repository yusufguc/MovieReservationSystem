package com.yusufguc.service.impl;

import com.yusufguc.dto.request.ShowtimeRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.dto.response.ShowtimeResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.model.Hall;
import com.yusufguc.model.Movie;
import com.yusufguc.model.Showtime;
import com.yusufguc.repository.HallRepository;
import com.yusufguc.repository.MovieRepository;
import com.yusufguc.repository.ShowTimeRepository;
import com.yusufguc.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {
    @Autowired
    private MovieRepository movieRepository;

   @Autowired
   private ShowTimeRepository showTimeRepository;

   @Autowired
   private HallRepository hallRepository;

    @Override
    public ShowtimeResponse addShowTime(Long movieId, ShowtimeRequest request) {

        Movie movieDb = movieRepository.findById(movieId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_MOVIE, movieId.toString())));

        Hall hallDb = hallRepository.findById(request.getHallId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_HALL, request.getHallId().toString())));

        LocalDateTime endTime =
                request.getStartTime().plusMinutes(movieDb.getDurationMinutes()).plusMinutes(15);

        List<Showtime> listHallShowtime = showTimeRepository.findByHallId(hallDb.getId());
        for (Showtime showtime:listHallShowtime){
            if (request.getStartTime().isBefore(showtime.getEndTime())
            && endTime.isAfter(showtime.getStartTime())){
                throw new BaseException(
                        new ErrorMessage(MessageType.SHOWTIME_CONFLICT, hallDb.getName()));
            }
        }
        Showtime showtime = toShowtime(request, movieDb, hallDb);
        Showtime savedShowTime = showTimeRepository.save(showtime);

        return toShowtimeResponse(savedShowTime);
    }

    @Override
    public ShowtimeResponse updateShowTime(Long movieId, Long showTimeId, ShowtimeRequest request) {
        Movie movieDb = movieRepository.findById(movieId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_MOVIE, movieId.toString())));

        Showtime showTimeDb = showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_SHOWTIME, showTimeId.toString())));

        Hall hallDb = hallRepository.findById(request.getHallId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_HALL, request.getHallId().toString())));

        LocalDateTime endTime =
                request.getStartTime().plusMinutes(movieDb.getDurationMinutes()).plusMinutes(15);

        List<Showtime> listHallShowtime = showTimeRepository.findByHallId(hallDb.getId());
        for (Showtime showtime:listHallShowtime){
            if (!showtime.getId().equals(showTimeDb.getId()) &&
                    request.getStartTime().isBefore(showtime.getEndTime()) &&
                    endTime.isAfter(showtime.getStartTime())){
                throw new BaseException(
                        new ErrorMessage(MessageType.SHOWTIME_CONFLICT, hallDb.getName()));
            }
        }
        showTimeDb.setMovie(movieDb);
        showTimeDb.setStartTime(request.getStartTime());
        showTimeDb.setHall(hallDb);
        showTimeDb.setEndTime(endTime);
        Showtime savedShowTime = showTimeRepository.save(showTimeDb);

        return toShowtimeResponse(savedShowTime);
    }

    @Override
    public void deleteShowTime( Long showTimeId) {
        Showtime showtimeDb = showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_SHOWTIME, showTimeId.toString())));
        showTimeRepository.delete(showtimeDb);

    }

    @Override
    public List<ShowtimeResponse> getShowTimeByDate(LocalDate date) {
        LocalDateTime startOfDate = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Showtime> listStartTimeBetween = showTimeRepository.findByStartTimeBetween(startOfDate, endOfDay);

        return  listStartTimeBetween.stream()
                .map(this::toShowtimeResponse)
                .toList();
    }


    // -----------------------
    // DTO Mapper
    // -----------------------

    private Showtime toShowtime(ShowtimeRequest request, Movie movie, Hall hall) {
        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setHall(hall);
        showtime.setStartTime(request.getStartTime().withSecond(0).withNano(0));
        showtime.setEndTime(request.getStartTime().plusMinutes(movie.getDurationMinutes()).withSecond(0).withNano(0));
        return showtime;
    }

    private ShowtimeResponse toShowtimeResponse(Showtime showtime) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setTitle(showtime.getMovie().getTitle());
        movieResponse.setDescription(showtime.getMovie().getDescription());
        movieResponse.setGenre(showtime.getMovie().getGenre());
        movieResponse.setDurationMinutes(showtime.getMovie().getDurationMinutes());

        ShowtimeResponse response = new ShowtimeResponse();
        response.setMovie(movieResponse);
        response.setHallName(showtime.getHall().getName());
        response.setStartTime(showtime.getStartTime());
        response.setEndTime(showtime.getEndTime());

        return response;
    }





}
