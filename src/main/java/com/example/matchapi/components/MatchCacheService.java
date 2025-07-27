package com.example.matchapi.components;

import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.exceptions.ObjectNotFoundException;
import com.example.matchapi.repositories.MatchOddsRepository;
import com.example.matchapi.repositories.MatchRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class MatchCacheService {

    private final MatchRepository matchRepository;
    private final MatchOddsRepository matchOddsRepository;

    public MatchCacheService(MatchRepository matchRepository, MatchOddsRepository matchOddsRepository) {
        this.matchRepository = matchRepository;
        this.matchOddsRepository = matchOddsRepository;
    }

    @Cacheable(value = "matches", key = "#id")
    public Match geMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Match with id: " + id + " not found"));
    }

    @Cacheable(value = "odds", key = "#id")
    public MatchOdds getMatchOddById(Long id) {
        return matchOddsRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Odd with id: " + id + " not found"));
    }
}
