package com.yusufguc.service;

import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;

public interface ReservationService {

    public ReservationResponse createReservation(ReservationRequest request);
}
