package com.yusufguc.repository;

import com.yusufguc.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    boolean existsByTitleIgnoreCase(String title);

}
