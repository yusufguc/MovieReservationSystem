package com.yusufguc.controller.impl;

import com.yusufguc.controller.MovieController;
import com.yusufguc.dto.request.MovieRequest;
import com.yusufguc.dto.response.MovieResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MovieControllerImpl extends RestBaseController implements MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/movies")
    @Override
    public RootEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest movieRequest) {

        return ok(movieService.addMovie(movieRequest));
    }

    @DeleteMapping("/movies/{id}")
    @Override
    public void deleteMovie(@PathVariable Long id) {
         movieService.deleteMovie(id);
    }

    @PutMapping("/movies/{id}")
    @Override
    public RootEntity<MovieResponse> updateMovie(@PathVariable Long id,
                                                 @Valid @RequestBody MovieRequest movieRequest) {
        return ok(movieService.updateMovie(id,movieRequest));
    }
}
