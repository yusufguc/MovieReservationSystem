package com.yusufguc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeResponse {

    private MovieResponse movie;

    private String hallName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
