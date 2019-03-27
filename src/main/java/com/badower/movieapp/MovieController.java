package com.badower.movieapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable long id) {
        return movieRepository.findById(id).map(ResponseEntity::ok).orElse((ResponseEntity.notFound().build()));
    }

    @GetMapping("/movies")
    public ResponseEntity<Iterable<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @GetMapping("/movies/filter")
    public ResponseEntity<List<Movie>> filter(@RequestParam(required = false) Integer year, @RequestParam(required = false) String title) {
        return ResponseEntity.ok(movieRepository.findByYearAndTitle(year, title));
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> postMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieRepository.save(movie));
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> putMovie(@PathVariable long id, @RequestBody Movie movie) {
        Optional<Movie> movie1 = movieRepository.findById(id).map(m -> {
            m.setTitle(movie.getTitle());
            m.setYear(movie.getYear());
            return m;
        });
        return movie1.map(movieRepository::save).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
