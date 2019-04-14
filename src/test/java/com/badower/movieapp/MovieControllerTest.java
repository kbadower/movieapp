package com.badower.movieapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void should_get_empty_movies() throws Exception {
        //when(service.greet()).thenReturn("Hello Mock");
        this.mockMvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_add_entity() throws Exception {
        Movie expectedMovie = new Movie();
        expectedMovie.setId(1L);
        expectedMovie.setYear(2005);
        expectedMovie.setTitle("TestTitle");


        this.mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"year\": 2005, \"title\": \"TestTitle\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(movieRepository).save(expectedMovie);


    }

    @Test
    public void should_get_entity() throws Exception {

        Movie expectedMovie = new Movie();
        expectedMovie.setId(1L);
        expectedMovie.setYear(2005);
        expectedMovie.setTitle("TestTitle");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(expectedMovie));

        this.mockMvc.perform(get("/movies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1, \"year\": 2005, \"title\": \"TestTitle\"}"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_update_entity() throws Exception{
        Movie expectedMovie = new Movie();
        expectedMovie.setId(1L);
        expectedMovie.setYear(2005);
        expectedMovie.setTitle("TestTitle");

        Movie expectedMovie2 = new Movie();
        expectedMovie.setId(1L);
        expectedMovie.setYear(2006);
        expectedMovie.setTitle("Title2");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(expectedMovie));

        this.mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"year\": 2006, \"title\": \"Title2\"}"))
                .andDo(print())
                .andExpect(status().isOk());



        verify(movieRepository).findById(1L);
        verify(movieRepository).save(expectedMovie2);
    }

    @Test
    public void should_put_new_entity() throws Exception{
        Movie expectedMovie = new Movie();
        expectedMovie.setId(1L);
        expectedMovie.setYear(2005);
        expectedMovie.setTitle("TestTitle");

        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        this.mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"year\": 2005, \"title\": \"TestTitle\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(movieRepository).findById(1L);
        verify(movieRepository).save(expectedMovie);

    }

    @Test
    public void should_delete_entity() throws Exception{
        Movie expectedMovie = new Movie();
        expectedMovie.setId(1L);
        expectedMovie.setYear(2005);
        expectedMovie.setTitle("TestTitle");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(expectedMovie));

        this.mockMvc.perform(delete("/movies/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(movieRepository).deleteById(1L);
    }

    @Test
    public void should_not_pass_year_validation() throws Exception {


        verify(movieRepository, never()).save(any());
    }


}