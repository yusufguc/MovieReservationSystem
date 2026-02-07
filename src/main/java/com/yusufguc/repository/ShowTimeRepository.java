package com.yusufguc.repository;

import com.yusufguc.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtime,Long> {

    List<Showtime> findByHallId(Long hallId);

    List<Showtime> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

}
