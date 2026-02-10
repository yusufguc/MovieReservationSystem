package com.yusufguc.controller;

import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;
import com.yusufguc.model.RootEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationController {
    public RootEntity<ReservationResponse> createReservation(ReservationRequest request);

    public RootEntity<ReservationResponse> cancelReservation(Long reservationId) ;

    public ResponseEntity<ReservationResponse> updateReservation(Long reservationId, ReservationRequest request) ;

    public RootEntity<List<ReservationResponse>> getAllReservation() ;

    public RootEntity<List<ReservationResponse>> getMyReservation() ;


}
