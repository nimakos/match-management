package com.example.matchapi;

import com.example.matchapi.controllers.MatchControllerImpl;
import com.example.matchapi.entities.Match;
import com.example.matchapi.enums.Sport;
import com.example.matchapi.services.MatchService;
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

import java.time.LocalDate;
import java.util.Collections;

import static com.example.matchapi.mappings.Mappings.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchControllerImpl.class)
public class MatchControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    private Match match;

    @BeforeEach
    public void setup() {
        match = new Match();
        match.setId(1L);
        match.setTeam_a("Team A");
        match.setTeam_b("Team B");
        match.setSport(Sport.FOOTBALL);
        match.setMatchDate(LocalDate.of(2025, 12, 1));
    }

    @Test
    public void testGetAllMatchesMatches() throws Exception {
        Mockito.when(matchService.getAll()).thenReturn(Collections.singletonList(match));

        mockMvc.perform(get(MATCHES + GET_MATCHES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetMatchById() throws Exception {
        Mockito.when(matchService.getById(1L)).thenReturn(match);

        mockMvc.perform(get(MATCHES + GET_MATCH.replace("{id}", String.valueOf(1))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team_a").value("Team A"));
    }

    @Test
    public void testCreateMatch() throws Exception {
        Mockito.when(matchService.create(any(Match.class))).thenReturn(match);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post(MATCHES + CREATE_MATCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(match)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.team_b").value("Team B"));
    }

    @Test
    public void testUpdateMatch() throws Exception {
        match.setTeam_a("Updated Team");

        Mockito.when(matchService.update(eq(1L), any(Match.class), any(Boolean.class))).thenReturn(match);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(put(MATCHES + UPDATE_MATCH.replace("{id}", String.valueOf(1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clearOdds", "true")
                        .content(mapper.writeValueAsString(match)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team_a").value("Updated Team"));
    }

    @Test
    public void testDeleteMatch() throws Exception {
        mockMvc.perform(delete(MATCHES + DELETE_MATCH.replace("{id}", String.valueOf(1))))
                .andExpect(status().isNoContent());
    }
}
