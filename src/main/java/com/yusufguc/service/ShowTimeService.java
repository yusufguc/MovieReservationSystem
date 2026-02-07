package com.yusufguc.service;

import com.yusufguc.dto.request.ShowtimeRequest;
import com.yusufguc.dto.response.ShowtimeResponse;

import java.time.LocalDate;
import java.util.List;

public interface ShowTimeService {

    public ShowtimeResponse addShowTime(Long movieId, ShowtimeRequest request);

    public  ShowtimeResponse updateShowTime(Long movieId,Long showTimeId,ShowtimeRequest request);

    public  void  deleteShowTime(Long showTimeId);

    public List<ShowtimeResponse> getShowTimeByDate(LocalDate date);
}
