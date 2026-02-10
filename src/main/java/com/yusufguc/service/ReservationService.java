package com.yusufguc.service;

import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {

    public ReservationResponse createReservation(ReservationRequest request);

    public ReservationResponse cancelReservation(Long reservationId);

    public ReservationResponse updateReservation(Long reservationId, ReservationRequest request) ;


    public List<ReservationResponse> getMyReservation();

    //----------------- PAGEABLE-------------------
    Page<ReservationResponse> getAllReservations(Pageable pageable);



}
