package com.yusufguc.controller.impl;

import com.yusufguc.controller.ReservationController;
import com.yusufguc.dto.request.ReservationRequest;
import com.yusufguc.dto.response.ReservationResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.service.ReservationService;
import com.yusufguc.utils.RestPageableEntity;
import com.yusufguc.utils.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationControllerImpl extends RestBaseController implements ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @PostMapping()
    @Override
    public RootEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        return ok(reservationService.createReservation(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @DeleteMapping("/id/{reservationId}")
    @Override
    public RootEntity<ReservationResponse> cancelReservation(@PathVariable Long reservationId) {
        return ok(reservationService.cancelReservation(reservationId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @PutMapping("/id/{reservationId}")
    @Override
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long reservationId,
                                                                 @RequestBody @Valid ReservationRequest request) {
        ReservationResponse response = reservationService.updateReservation(reservationId, request);
        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping()
//    @Override
//    public RootEntity<List<ReservationResponse>> getAllReservation() {
//        return ok(reservationService.getAllReservations());
//    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/my")
    @Override
    public RootEntity<List<ReservationResponse>> getMyReservation() {
        return ok(reservationService.getMyReservation());
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/pageable")
    public RootEntity<RestPageableEntity<ReservationResponse>> getAllReservations(RestPageableRequest pageable){

        Page<ReservationResponse> page =
                reservationService.getAllReservations(toPageable(pageable));

        RestPageableEntity<ReservationResponse> response =
                toPageableResponse(page, page.getContent());

        return ok(response);
    }

}
