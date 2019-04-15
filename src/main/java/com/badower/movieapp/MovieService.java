package com.badower.movieapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    Optional<Movie> getMovie(long id) {
        return movieRepository.findById(id);
    }

    Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    List<Movie> filter(Integer year, String title) {
        return movieRepository.findByYearAndTitle(year, title);
    }

    Movie postMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    Movie putMovie(long id, Movie movie) {
        Movie updatedMovie = movieRepository.findById(id).map(m -> {
            m.setTitle(movie.getTitle());
            m.setYear(movie.getYear());
            return m;
        }).orElse(movie);
        return movieRepository.save(updatedMovie);
    }

    void deleteMovie(long id) {
        movieRepository.deleteById(id);
    }
}
