package com.badower.movieapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {


    private MovieRepository movieRepository;
    private MovieService movieService;

    @Before
    public void setUp() throws Exception {
        this.movieRepository = Mockito.mock(MovieRepository.class);
        this.movieService = new MovieService(movieRepository);
    }

    public Movie movie(Long id, int year, String title) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setYear(year);
        movie.setTitle(title);
        return movie;
    }

    @Test
    public void should_add_entity() throws Exception {
        //Given
        Long movieId = 1L;
        Movie expectedMovie = movie(movieId, 2005, "TestTitle");
        BDDMockito.given(movieRepository.save(expectedMovie)).willReturn(expectedMovie);

        //When
        Movie result = this.movieService.saveMovie(expectedMovie);

        //Then
        assertThat(result).isEqualTo(expectedMovie);
        verify(movieRepository).save(expectedMovie);
    }

    @Test
    public void should_get_entity() throws Exception {
        //Given
        Long movieId = 1L;
        Movie expectedMovie = movie(movieId, 2005, "TestTitle");
        BDDMockito.given(movieRepository.findById(movieId)).willReturn(Optional.of(expectedMovie));

        //When
        Optional<Movie> result = this.movieService.getMovie(movieId);

        //Then
        assertThat(result).isEqualTo(Optional.of(expectedMovie));
        verify(movieRepository).findById(movieId);
    }

    @Test
    public void should_update_entity() throws Exception {
        //Given
        Long movieId = 1L;
        Movie oldMovie = movie(movieId, 2005, "TestTitle");
        Movie newMovie = movie(movieId, 2006, "Title2");
        BDDMockito.given(movieRepository.findById(movieId)).willReturn(Optional.of(oldMovie));
        BDDMockito.given(movieRepository.save(newMovie)).willReturn(newMovie);

        //When
        Movie result = this.movieService.updateMovie(movieId,newMovie);

        //Then
        assertThat(result).isEqualTo(newMovie);
        verify(movieRepository).findById(movieId);
        verify(movieRepository).save(newMovie);
    }

    @Test
    public void should_create_new_entity_for_not_existing_id() throws Exception {
        //Given
        Long movieId = 1L;
        Movie newMovie = movie(movieId, 2006, "Title2");
        BDDMockito.given(movieRepository.findById(movieId)).willReturn(Optional.empty());
        BDDMockito.given(movieRepository.save(newMovie)).willReturn(newMovie);

        //When
        Movie result = this.movieService.updateMovie(movieId,newMovie);

        //Then
        assertThat(result).isEqualTo(newMovie);
        verify(movieRepository).findById(movieId);
        verify(movieRepository).save(newMovie);
    }

    @Test
    public void should_delete_entity() throws Exception {
        //Given
        Long movieId = 1L;

        //When
        this.movieService.deleteMovie(movieId);

        //Then
        verify(movieRepository).deleteById(movieId);
    }

    @Test
    public void should_not_pass_year_validation() throws Exception {
        verify(movieRepository, never()).save(any());
    }

}