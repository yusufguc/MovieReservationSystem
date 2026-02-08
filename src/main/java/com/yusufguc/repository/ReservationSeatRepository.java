package com.yusufguc.repository;

import com.yusufguc.model.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat,Long> {

    List<ReservationSeat> findByShowtimeId(Long showtimeId);

}
