package com.yusufguc.repository;

import com.yusufguc.model.Movie;
import com.yusufguc.model.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    boolean existsByTitleIgnoreCase(String title);

    List<Movie> findByGenre(Genre genre);

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findAllByGenre(Genre genre, Pageable pageable);



}
