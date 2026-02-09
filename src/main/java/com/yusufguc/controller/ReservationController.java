package com.yusufguc.controller;

import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;
import com.yusufguc.model.RootEntity;

public interface ReservationController {
    public RootEntity<ReservationResponse> createReservation(ReservationRequest request);
}
