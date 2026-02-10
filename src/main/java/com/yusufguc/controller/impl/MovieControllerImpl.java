package com.yusufguc.controller.impl;

import com.yusufguc.controller.MovieController;
import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.model.enums.Genre;
import com.yusufguc.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MovieControllerImpl extends RestBaseController implements MovieController {

    @Autowired
    private MovieService movieService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movies")
    @Override
    public RootEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest movieRequest) {

        return ok(movieService.addMovie(movieRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/movies/{id}")
    @Override
    public void deleteMovie(@PathVariable Long id) {
         movieService.deleteMovie(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/movies/{id}")
    @Override
    public RootEntity<MovieResponse> updateMovie(@PathVariable Long id,
                                                 @Valid @RequestBody MovieRequest movieRequest) {
        return ok(movieService.updateMovie(id,movieRequest));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/movies")
    @Override
    public RootEntity<List<MovieResponse>> getAllMovies() {

        return ok(movieService.getAllMovies());
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/movies/id/{id}")
    @Override
    public RootEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ok(movieService.getMovieById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/movies/genre/{genre}")
    @Override
    public RootEntity<List<MovieResponse>> getAllMoviesByGenre(@PathVariable  Genre genre) {
        return ok(movieService.getAllMoviesByGenre(genre));
    }
}
