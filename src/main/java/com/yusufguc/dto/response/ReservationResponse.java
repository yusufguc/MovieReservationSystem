package com.yusufguc.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReservationResponse {

    private Long reservationId;
    private Long showtimeId;
    private List<Long> seatIds;
    private String status;
    private LocalDateTime createdAt;

}
