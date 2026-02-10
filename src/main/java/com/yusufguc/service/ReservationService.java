package com.yusufguc.service;

import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;

import java.util.List;

public interface ReservationService {

    public ReservationResponse createReservation(ReservationRequest request);

    public ReservationResponse cancelReservation(Long reservationId);

    public ReservationResponse updateReservation(Long reservationId, ReservationRequest request) ;

    public List<ReservationResponse> getAllReservations();

    public List<ReservationResponse> getMyReservation();


}
