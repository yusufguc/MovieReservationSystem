package com.yusufguc.controller.impl;

import com.yusufguc.controller.ReservationController;
import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationControllerImpl extends RestBaseController implements ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping()
    @Override
    public RootEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        return ok(reservationService.createReservation(request));
    }
}
