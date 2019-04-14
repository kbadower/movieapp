package com.badower.movieapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable long id) {
        return movieService.getMovie(id);
    }

    @GetMapping("/movies")
    public ResponseEntity<Iterable<Movie>> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/movies/filter")
    public ResponseEntity<List<Movie>> filter(@RequestParam(required = false) Integer year, @RequestParam(required = false) String title) {
        return movieService.filter(year, title);
    }

    @PostMapping("/movies")

    public ResponseEntity<Movie> postMovie(@Valid @RequestBody Movie movie) {
        return movieService.postMovie(movie);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> putMovie(@PathVariable long id, @RequestBody Movie movie) {
        return movieService.putMovie(id, movie);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
        return movieService.deleteMovie(id);
    }

}
