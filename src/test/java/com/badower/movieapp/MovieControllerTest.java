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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void should_get_empty_movies() throws Exception{
        //when(service.greet()).thenReturn("Hello Mock");
        this.mockMvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_add_entity() throws Exception{
        //when(service.greet()).thenReturn("Hello Mock");
        this.mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"year\": 2005, \"title\": \"TestTitle\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/movies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1, \"year\": 2005, \"title\": \"TestTitle\"}"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }
}