package com.yusufguc.service.impl;

import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.model.*;
import com.yusufguc.model.enums.ReservationStatus;
import com.yusufguc.repository.*;
import com.yusufguc.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ShowTimeRepository showTimeRepository;
    private final SeatRepository seatRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

//---------------------------createReservation-----------------------------
    @Transactional
    @Override
    public ReservationResponse createReservation(ReservationRequest request) {

        Showtime showtimeDb = showTimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_SHOWTIME, request.getShowtimeId().toString())));

        if (showtimeDb.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.SHOWTIME_ALREADY_STARTED,""));
        }

        List<Seat> seatList = seatRepository.findAllById(request.getSeatIds());
        if (seatList.size() != request.getSeatIds().size()){
            throw new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_SEATS,""));
        }

        List<ReservationSeat> reservedSeats =
                reservationSeatRepository
                        .findByReservation_Showtime_Id(showtimeDb.getId());

        List<Long> reservedSeatIds = reservedSeats.stream()
                .map(rs -> rs.getSeat().getId())
                .toList();

        for (Long seatId : request.getSeatIds()) {
            if (reservedSeatIds.contains(seatId)) {
                throw new BaseException(
                        new ErrorMessage(MessageType.SEAT_ALREADY_RESERVED,""));
            }
        }

        Reservation reservation = mapRequestToReservation(request, showtimeDb);

        List<ReservationSeat> reservationSeats = seatList.stream()
                .map(seat -> {
                    ReservationSeat rs = new ReservationSeat();
                    rs.setReservation(reservation);
                    rs.setSeat(seat);
                    return rs;
                }).toList();

        try {
            reservationRepository.save(reservation);
            reservationSeatRepository.saveAll(reservationSeats);
        }catch (DataIntegrityViolationException e ){
            throw new BaseException(new ErrorMessage(MessageType.SEAT_ALREADY_RESERVED,""));
        }

        reservation.setReservationSeats(reservationSeats);
        ReservationResponse response = mapReservationToResponse(reservation);

        return response;
    }

//---------------------------cancelReservation-----------------------

    @Override
    @Transactional
    public ReservationResponse cancelReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESERVATION_NOT_FOUND, reservationId.toString())));

        if (reservation.getShowtime().getStartTime().isBefore(LocalDateTime.now())) {
            throw  new BaseException(
                    new ErrorMessage(MessageType.CANNOT_CANCEL_PAST_RESERVATION,reservationId.toString()));
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        return mapReservationToResponse(reservation);
    }

//---------------------------updateReservation-----------------------

    @Transactional
    @Override
    public ReservationResponse updateReservation(Long reservationId, ReservationRequest request) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESERVATION_NOT_FOUND, reservationId.toString())
                ));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!reservation.getUser().getUsername().equals(username)) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_ALLOWED, ""));
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new BaseException(new ErrorMessage(MessageType.RESERVATION_ALREADY_CANCELLED, ""));
        }


        if (reservation.getShowtime().getStartTime().isBefore(LocalDateTime.now())) {
            throw new BaseException(new ErrorMessage(MessageType.SHOWTIME_ALREADY_STARTED, ""));
        }

        List<Seat> seatList = seatRepository.findAllById(request.getSeatIds());
        if (seatList.size() != request.getSeatIds().size()) {
            throw new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_SEATS, ""));
        }

        List<Long> reservedSeatIds = reservationSeatRepository
                .findByReservation_Showtime_Id(reservation.getShowtime().getId())
                .stream()
                .map(rs -> rs.getSeat().getId())
                .filter(id -> !reservation.getReservationSeats().stream()
                        .map(r -> r.getSeat().getId())
                        .collect(Collectors.toList())
                        .contains(id))
                .collect(Collectors.toList());

        for (Long seatId : request.getSeatIds()) {
            if (reservedSeatIds.contains(seatId)) {
                throw new BaseException(new ErrorMessage(MessageType.SEAT_ALREADY_RESERVED, ""));
            }
        }

        reservation.getReservationSeats().clear();

        List<ReservationSeat> newReservationSeats = seatList.stream()
                .map(seat -> {
                    ReservationSeat rs = new ReservationSeat();
                    rs.setReservation(reservation);
                    rs.setSeat(seat);
                    return rs;
                })
                .collect(Collectors.toList());

        reservation.getReservationSeats().addAll(newReservationSeats);

        reservationRepository.save(reservation);

        return mapReservationToResponse(reservation);
    }

//---------------------------getAllReservation-----------------------

//    @Transactional
//    @Override
//    public List<ReservationResponse> getAllReservations() {
//        List<Reservation> reservations = reservationRepository.findAll();
//
//        return reservations.stream()
//                .map(this::mapReservationToResponse)
//                .toList();
//    }

//---------------------------getMyReservation-----------------------

    @Transactional
    @Override
    public List<ReservationResponse> getMyReservation() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Reservation> reservations = reservationRepository.findByUser_Username(username);


        return reservations.stream()
                .map(this::mapReservationToResponse)
                .toList();
    }

//---------------------------PAGEABLE-----------------------
    @Transactional
    @Override
    public Page<ReservationResponse> getAllReservations(Pageable pageable) {

        Page<Reservation> reservationsPage = reservationRepository.findAll(pageable);

        return reservationsPage.map(this::mapReservationToResponse);
    }


    // -----------------------
    // DTO Mapper
    // -----------------------
    private Reservation mapRequestToReservation(ReservationRequest request, Showtime showtime) {
        Reservation reservation = new Reservation();
        reservation.setShowtime(showtime);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreatedAt(LocalDateTime.now());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, username)
                ));
        reservation.setUser(user);
        return reservation;
    }

    private ReservationResponse mapReservationToResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setReservationId(reservation.getId());
        response.setShowtimeId(reservation.getShowtime().getId());
        response.setSeatIds(reservation.getReservationSeats().stream()
                .map(rs -> rs.getSeat().getId())
                .toList()
        );
        response.setStatus(reservation.getStatus().name());
        response.setCreatedAt(reservation.getCreatedAt());
        return response;
    }

}
