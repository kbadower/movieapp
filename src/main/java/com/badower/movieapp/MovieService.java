package com.badower.movieapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    ResponseEntity<Movie> getMovie(@PathVariable long id) {
        return movieRepository.findById(id).map(ResponseEntity::ok).orElse((ResponseEntity.notFound().build()));
    }

    ResponseEntity<Iterable<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    ResponseEntity<List<Movie>> filter(@RequestParam(required = false) Integer year, @RequestParam(required = false) String title) {
        return ResponseEntity.ok(movieRepository.findByYearAndTitle(year, title));
    }

    ResponseEntity<Movie> postMovie(@Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieRepository.save(movie));
    }

    ResponseEntity<Movie> putMovie(@PathVariable long id, @RequestBody Movie movie) {
        Movie updatedMovie = movieRepository.findById(id).map(m -> {
            m.setTitle(movie.getTitle());
            m.setYear(movie.getYear());
            return m;
        }).orElse(movie);
        return ResponseEntity.ok(movieRepository.save(updatedMovie));
    }

    ResponseEntity<Void> deleteMovie(@PathVariable long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
