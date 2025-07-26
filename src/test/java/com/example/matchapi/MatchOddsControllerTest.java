package com.example.matchapi;

import com.example.matchapi.controllers.MatchOddsController;
import com.example.matchapi.services.MatchOddsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchOddsController.class)
public class MatchOddsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchOddsService matchOddsService;

    @Test
    public void testGetAllMatchOdds() throws Exception {
        mockMvc.perform(get("/odds"))
                .andExpect(status().isOk());
    }
}