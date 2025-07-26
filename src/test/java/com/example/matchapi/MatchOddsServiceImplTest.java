package com.example.matchapi;

import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.repositories.MatchOddsRepository;
import com.example.matchapi.services.MatchOddsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MatchOddsServiceImplTest {
    @Mock
    MatchOddsRepository matchOddsRepository;

    @InjectMocks
    MatchOddsServiceImpl matchOddsService;

    public MatchOddsServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMatchOddsById() {
        MatchOdds odds = new MatchOdds();
        odds.setId(1L);
        when(matchOddsRepository.findById(1L)).thenReturn(Optional.of(odds));

        MatchOdds result = matchOddsService.getById(1L);
        assertEquals(1L, result.getId());
    }
}