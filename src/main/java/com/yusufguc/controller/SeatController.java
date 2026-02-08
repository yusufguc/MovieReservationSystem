package com.yusufguc.controller;

import com.yusufguc.model.RootEntity;
import com.yusufguc.model.Seat;

import java.util.List;

public interface SeatController {

    public RootEntity<List<Seat>> getAvailableSeats(Long showtimeId);
}
