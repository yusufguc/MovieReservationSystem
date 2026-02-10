package com.yusufguc.controller.impl;

import com.yusufguc.controller.SeatController;
import com.yusufguc.model.RootEntity;
import com.yusufguc.model.Seat;
import com.yusufguc.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SeatControllerImpl extends  RestBaseController implements SeatController {
    @Autowired
    private SeatService seatService;


    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/showtimes/{showtimeId}/available-seats")
    @Override
    public RootEntity<List<Seat>> getAvailableSeats(@PathVariable Long showtimeId) {
        return ok(seatService.getAvailableSeats(showtimeId));
    }
}
