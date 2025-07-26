package com.example.matchapi;

import com.example.matchapi.controllers.MatchOddsControllerImpl;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.services.MatchOddsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchOddsControllerImpl.class)
public class MatchOddsControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchOddsService matchOddsService;

    private MatchOdds odds;

    @BeforeEach
    public void setup() {
        odds = new MatchOdds();
        odds.setId(1L);
        odds.setSpecifier("1");
        odds.setOdd(BigDecimal.valueOf(1.80));
    }

    @Test
    public void testGetGetAllOddsMatchOdds() throws Exception {
        Mockito.when(matchOddsService.getAll()).thenReturn(Collections.singletonList(odds));

        mockMvc.perform(get("/odds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetMatchOddsById() throws Exception {
        Mockito.when(matchOddsService.getById(1L)).thenReturn(odds);

        mockMvc.perform(get("/odds/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specifier").value("1"));
    }

    @Test
    public void testCreateMatchOdds() throws Exception {
        Mockito.when(matchOddsService.create(eq(2L), any(MatchOdds.class))).thenReturn(odds);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/odds?matchId=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(odds)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.odd").value(1.80));
    }

    @Test
    public void testUpdateMatchOdds() throws Exception {
        odds.setOdd(BigDecimal.valueOf(2.00));
        Mockito.when(matchOddsService.update(eq(1L), any(MatchOdds.class))).thenReturn(odds);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(put("/odds/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(odds)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.odd").value(2.00));
    }

    @Test
    public void testDeleteMatchOdds() throws Exception {
        mockMvc.perform(delete("/odds/1"))
                .andExpect(status().isNoContent());
    }
}
