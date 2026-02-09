package com.yusufguc.service.impl;

import com.yusufguc.model.ReservationSeat;
import com.yusufguc.model.Seat;
import com.yusufguc.repository.ReservationSeatRepository;
import com.yusufguc.repository.SeatRepository;
import com.yusufguc.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationSeatRepository reservationSeatRepository;

    @Override
    public List<Seat> getAvailableSeats(Long showtimeId) {
        List<Seat> allSeats = seatRepository.findAll();

        List<ReservationSeat> reservedSeats =
                reservationSeatRepository.findByReservation_Showtime_Id(showtimeId);

        List<Long> reservedSeatIds = reservedSeats.stream()
                .map(rs -> rs.getSeat().getId())
                .toList();

        return allSeats.stream()
                .filter(seat -> !reservedSeatIds.contains(seat.getId()))
                .toList();
    }
}
