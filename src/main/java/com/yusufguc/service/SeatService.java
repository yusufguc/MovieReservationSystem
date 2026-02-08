package com.yusufguc.service;

import com.yusufguc.model.Seat;

import java.util.List;

public interface SeatService {

    public List<Seat> getAvailableSeats(Long showtimeId);
}
