package com.yusufguc.service.impl;

import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.model.*;
import com.yusufguc.model.enums.ReservationStatus;
import com.yusufguc.repository.ReservationRepository;
import com.yusufguc.repository.ReservationSeatRepository;
import com.yusufguc.repository.SeatRepository;
import com.yusufguc.repository.ShowTimeRepository;
import com.yusufguc.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationSeatRepository reservationSeatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    @Override
    public ReservationResponse createReservation(ReservationRequest request) {

        Showtime showtimeDb = showTimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_SHOWTIME, request.getShowtimeId().toString())));

        List<Seat> seatList = seatRepository.findAllById(request.getSeatIds());

        if (seatList.size() != request.getSeatIds().size()){
            throw new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_SEATS,""));
        }

        if (reservationSeatRepository
                .existsByShowtimeIdAndSeatIdIn(showtimeDb.getId(),request.getSeatIds())){
            throw new BaseException(new ErrorMessage(MessageType.SEAT_ALREADY_RESERVED,""));
        }


        Reservation reservation=new Reservation();
        reservation.setShowtime(showtimeDb);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreatedAt(LocalDateTime.now());
        reservationRepository.save(reservation);

        List<ReservationSeat> reservationSeats = seatList.stream()
                .map(seat -> {
                    ReservationSeat rs = new ReservationSeat();
                    rs.setReservation(reservation);
                    rs.setSeat(seat);
                    rs.setShowtime(showtimeDb);
                    return rs;
                }).toList();

        try {
            reservationSeatRepository.saveAll(reservationSeats);
        }catch (DataIntegrityViolationException e ){
            throw new BaseException(new ErrorMessage(MessageType.SEAT_ALREADY_RESERVED,""));
        }

        reservation.setReservationSeats(reservationSeats);

        ReservationResponse response = new ReservationResponse();
        response.setReservationId(reservation.getId());
        response.setShowtimeId(showtimeDb.getId());
        response.setSeatIds(
                reservationSeats.stream()
                        .map(rs -> rs.getSeat().getId())
                        .toList()
        );
        response.setStatus(reservation.getStatus().name());
        response.setCreatedAt(reservation.getCreatedAt());

        return response;
    }












}
