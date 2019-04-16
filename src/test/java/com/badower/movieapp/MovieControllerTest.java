package com.badower.movieapp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MovieControllerTest {

    private MockMvc mockMvc;
    private MovieService movieService;


    @Before
    public void setUp() throws Exception {
        this.movieService = mock(MovieService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(movieService)).build();
    }

    private Movie movie(Long id, int year, String title) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setYear(year);
        movie.setTitle(title);
        return movie;
    }

    @Test
    public void should_get_empty_movies() throws Exception {
        this.mockMvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_add_entity() throws Exception {
        Long movieId = 1L;
        Movie expectedMovie = movie(movieId, 2005, "TestTitle");


        this.mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"year\": 2005, \"title\": \"TestTitle\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(movieService).saveMovie(expectedMovie);


    }

    @Test
    public void should_get_entity() throws Exception {
        Long movieId = 1L;
        Movie expectedMovie = movie(movieId, 2005, "TestTitle");

        when(movieService.getMovie(1L)).thenReturn(Optional.of(expectedMovie));

        this.mockMvc.perform(get("/movies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1, \"year\": 2005, \"title\": \"TestTitle\"}"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_update_entity() throws Exception {
        Long movieId = 1L;
        Movie movie = movie(movieId, 2006, "NewTestTitle");

        when(movieService.updateMovie(movieId, movie)).thenReturn(movie);

        this.mockMvc.perform(put("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"year\": 2006, \"title\": \"NewTestTitle\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(movieService).updateMovie(movieId, movie);
    }

    @Test
    public void should_delete_entity() throws Exception {
        Movie expectedMovie = new Movie();
        expectedMovie.setId(1L);
        expectedMovie.setYear(2005);
        expectedMovie.setTitle("TestTitle");

        when(movieService.getMovie(1L)).thenReturn(Optional.of(expectedMovie));

        this.mockMvc.perform(delete("/movies/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(movieService).deleteMovie(1L);
    }


}