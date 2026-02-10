package com.yusufguc.controller.impl;

import com.yusufguc.controller.ShowTimeController;
import com.yusufguc.dto.request.ShowtimeRequest;
import com.yusufguc.dto.response.ShowtimeResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.service.ShowTimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShowTimeControllerImpl extends RestBaseController implements ShowTimeController {
    @Autowired
    private ShowTimeService showTimeService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movies/{movieId}/showtimes")
    @Override
    public RootEntity<ShowtimeResponse> addShowTime(@PathVariable Long movieId,
                                                    @Valid @RequestBody ShowtimeRequest request) {
        return ok(showTimeService.addShowTime(movieId,request)) ;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/movies/{movieId}/showtimes/{showTimeId}")
    @Override
    public RootEntity<ShowtimeResponse> updateShowTime(@PathVariable Long movieId,
                                                       @PathVariable Long showTimeId,
                                                       @Valid @RequestBody ShowtimeRequest request) {
        return ok(showTimeService.updateShowTime(movieId,showTimeId,request)) ;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/showtime/{showTimeId}")
    @Override
    public void deleteShowTime(@PathVariable Long showTimeId) {
        showTimeService.deleteShowTime(showTimeId);
    }


    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/showtimes/by-date")
    @Override
    public RootEntity<List<ShowtimeResponse>> getShowTimeByDate(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ok(showTimeService.getShowTimeByDate(date));
    }
}
