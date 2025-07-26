package com.example.matchapi;

import com.example.matchapi.entities.Match;
import com.example.matchapi.repositories.MatchRepository;
import com.example.matchapi.services.MatchServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MatchServiceImplTest {
    @Mock
    MatchRepository matchRepository;

    @InjectMocks
    MatchServiceImpl matchService;

    public MatchServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMatchById() {
        Match match = new Match();
        match.setId(1L);
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        Match result = matchService.getById(1L);
        assertEquals(1L, result.getId());
    }
}