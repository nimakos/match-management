package com.example.matchapi.services;

import com.example.matchapi.components.MatchCacheService;
import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.exceptions.BadRequestException;
import com.example.matchapi.exceptions.ObjectNotFoundException;
import com.example.matchapi.repositories.MatchOddsRepository;
import com.example.matchapi.repositories.MatchRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchOddsServiceImpl implements MatchOddsService {

    private final MatchOddsRepository oddsRepo;
    private final MatchRepository matchRepo;
    private final MatchCacheService matchCacheService;

    public MatchOddsServiceImpl(MatchOddsRepository oddsRepo, MatchRepository matchRepo, MatchCacheService matchCacheService) {
        this.oddsRepo = oddsRepo;
        this.matchRepo = matchRepo;
        this.matchCacheService = matchCacheService;
    }

    @Override
    public List<MatchOdds> getAll() {
        return oddsRepo.findAll();
    }

    @Override
    public MatchOdds getById(Long id) {
        return matchCacheService.getMatchOddById(id);
    }

    @Override
    @Transactional
    public MatchOdds create(Long matchId, MatchOdds odds) {
        Match match = matchRepo.findById(matchId)
                .orElseThrow(() -> new ObjectNotFoundException("Match with id: " + matchId + " not found"));
        odds.setMatch(match);
        return oddsRepo.save(odds);
    }

    @Override
    @Transactional
    @CacheEvict(value = "odds", key = "#id")
    public MatchOdds update(Long id, MatchOdds input) {
        MatchOdds existing = matchCacheService.getMatchOddById(id);

        if (input.getMatch() != null && !existing.getMatch().getId().equals(input.getMatch().getId())) {
            throw new BadRequestException("Cannot change associated Match");
        }

        existing.setOdd(input.getOdd());
        existing.setSpecifier(input.getSpecifier());
        return oddsRepo.save(existing);
    }

    @Override
    @Transactional
    @CacheEvict(value = "odds", key = "#id")
    public void delete(Long id) {
        MatchOdds odds = matchCacheService.getMatchOddById(id);
        oddsRepo.delete(odds);
    }
}

