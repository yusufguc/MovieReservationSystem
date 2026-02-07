package com.yusufguc.controller;

import com.yusufguc.dto.request.ShowtimeRequest;
import com.yusufguc.dto.response.ShowtimeResponse;
import com.yusufguc.model.RootEntity;

import java.time.LocalDate;
import java.util.List;

public interface ShowTimeController {

    public RootEntity<ShowtimeResponse> addShowTime(Long movieId, ShowtimeRequest request);

    public RootEntity<ShowtimeResponse> updateShowTime(Long movieId, Long showTimeId, ShowtimeRequest request) ;

    public void deleteShowTime( Long showTimeId);

    public RootEntity<List<ShowtimeResponse>> getShowTimeByDate(LocalDate date);


}
