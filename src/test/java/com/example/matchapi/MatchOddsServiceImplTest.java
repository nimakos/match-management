package com.example.matchapi;

import com.example.matchapi.components.MatchCacheService;
import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.exceptions.BadRequestException;
import com.example.matchapi.exceptions.ObjectNotFoundException;
import com.example.matchapi.repositories.MatchOddsRepository;
import com.example.matchapi.repositories.MatchRepository;
import com.example.matchapi.services.MatchOddsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MatchOddsServiceImplTest {

    @Mock
    MatchOddsRepository oddsRepo;

    @Mock
    MatchRepository matchRepo;

    @Mock
    private MatchCacheService matchCacheService;

    @InjectMocks
    MatchOddsServiceImpl matchOddsService;

    private Match match;
    private MatchOdds odds;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        match = new Match();
        match.setId(1L);

        odds = new MatchOdds();
        odds.setId(1L);
        odds.setOdd(BigDecimal.valueOf(1.85));
        odds.setSpecifier("1");
        odds.setMatch(match);
    }

    @Test
    public void testGetAll() {
        when(oddsRepo.findAll()).thenReturn(Collections.singletonList(odds));
        assertEquals(1, matchOddsService.getAll().size());
    }

    @Test
    public void testGetByIdFound() {
        when(matchCacheService.getMatchOddById(1L)).thenReturn(odds);
        MatchOdds result = matchOddsService.getById(1L);
        assertEquals(BigDecimal.valueOf(1.85), result.getOdd());
    }

    @Test
    public void testGetByIdNotFound() {
        when(matchCacheService.getMatchOddById(99L))
                .thenThrow(new ObjectNotFoundException("Odd with id: 1 not found"));
        assertThrows(ObjectNotFoundException.class, () -> matchOddsService.getById(99L));
    }

    @Test
    public void testCreateOddsWithValidMatch() {
        when(matchRepo.findById(1L)).thenReturn(Optional.of(match));
        when(oddsRepo.save(any())).thenReturn(odds);

        MatchOdds result = matchOddsService.create(1L, odds);
        assertEquals(match, result.getMatch());
    }

    @Test
    public void testCreateOddsWithInvalidMatch() {
        when(matchRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> matchOddsService.create(1L, odds));
    }

    @Test
    public void testUpdateOddsValid() {
        when(matchCacheService.getMatchOddById(1L)).thenReturn(odds);
        when(oddsRepo.save(any())).thenReturn(odds);

        MatchOdds input = new MatchOdds();
        input.setOdd(BigDecimal.valueOf(2.0));
        input.setSpecifier("X");
        input.setMatch(match); // same match

        MatchOdds updated = matchOddsService.update(1L, input);
        assertEquals("X", updated.getSpecifier());
        assertEquals(BigDecimal.valueOf(2.0), updated.getOdd());
    }

    @Test
    public void testUpdateOddsChangingMatchThrows() {
        Match differentMatch = new Match();
        differentMatch.setId(99L);

        MatchOdds input = new MatchOdds();
        input.setOdd(BigDecimal.valueOf(2.5));
        input.setSpecifier("2");
        input.setMatch(differentMatch); // different match

        when(matchCacheService.getMatchOddById(1L)).thenReturn(odds);

        assertThrows(BadRequestException.class, () -> matchOddsService.update(1L, input));
    }

    @Test
    public void testDeleteOdds() {
        when(matchCacheService.getMatchOddById(1L)).thenReturn(odds);

        matchOddsService.delete(1L);
        verify(oddsRepo, times(1)).delete(odds);
    }
}
