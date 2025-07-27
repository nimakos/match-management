package com.example.matchapi;

import com.example.matchapi.components.MatchCacheService;
import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.enums.Sport;
import com.example.matchapi.exceptions.ObjectNotFoundException;
import com.example.matchapi.repositories.MatchRepository;
import com.example.matchapi.services.MatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MatchCacheService matchCacheService;

    @InjectMocks
    private MatchServiceImpl matchService;

    private Match match;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        match = new Match();
        match.setId(1L);
        match.setTeam_a("Team A");
        match.setTeam_b("Team B");
        match.setSport(Sport.FOOTBALL);
        match.setMatchDate(LocalDate.of(2025, 12, 1));
        match.setMatchTime(LocalTime.of(15, 30));
        match.setDescription("Test match");
    }

    @Test
    public void testGetMatchById() {
        when(matchCacheService.geMatchById(1L)).thenReturn(match);
        Match result = matchService.getById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetMatchByIdNotFound() {
        when(matchCacheService.geMatchById(99L))
                .thenThrow(new ObjectNotFoundException("Match with id: 1 not found"));
        assertThrows(ObjectNotFoundException.class, () -> matchService.getById(99L));
    }

    @Test
    public void testGetAllMatches() {
        when(matchRepository.findAll()).thenReturn(Collections.singletonList(match));
        List<Match> all = matchService.getAll();
        assertEquals(1, all.size());
    }

    @Test
    public void testCreateMatchWithOdds() {
        MatchOdds odds = new MatchOdds();
        odds.setId(1L);
        odds.setOdd(BigDecimal.valueOf(1.85));
        odds.setSpecifier("1");

        match.setMatchOdds(new ArrayList<>(List.of(odds)));
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        Match result = matchService.create(match);
        assertEquals("1", result.getMatchOdds().get(0).getSpecifier());
        assertEquals(result, result.getMatchOdds().get(0).getMatch());
    }

    @Test
    public void testUpdateMatch() {
        Match existing = new Match();
        existing.setId(1L);
        existing.setMatchOdds(new ArrayList<>());

        when(matchCacheService.geMatchById(1L)).thenReturn(existing);
        when(matchRepository.save(any())).thenReturn(existing);

        MatchOdds newOdds = new MatchOdds();
        newOdds.setSpecifier("X");
        newOdds.setOdd(BigDecimal.valueOf(2.15));

        match.setMatchOdds(List.of(newOdds));

        Match updated = matchService.update(1L, match, true);

        assertEquals("X", updated.getMatchOdds().get(0).getSpecifier());
        assertEquals("Team A", updated.getTeam_a());
    }

    @Test
    public void testDeleteMatch() {
        when(matchCacheService.geMatchById(1L)).thenReturn(match);
        matchService.delete(1L);
        verify(matchRepository).delete(match);
    }

    @Test
    public void testDeleteMatchNotFound() {
        when(matchCacheService.geMatchById(1L))
                .thenThrow(new ObjectNotFoundException("Match with id: 1 not found"));
        assertThrows(ObjectNotFoundException.class, () -> matchService.delete(1L));
    }
}
