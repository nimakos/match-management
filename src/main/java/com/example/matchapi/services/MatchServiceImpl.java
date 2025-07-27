package com.example.matchapi.services;

import com.example.matchapi.components.MatchCacheService;
import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.repositories.MatchRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchCacheService matchCacheService;

    public MatchServiceImpl(MatchRepository matchRepository, MatchCacheService matchCacheService) {
        this.matchRepository = matchRepository;
        this.matchCacheService = matchCacheService;
    }

    @Override
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @Override
    public Match getById(Long id) {
        return matchCacheService.geMatchById(id);
    }

    @Override
    @Transactional
    public Match create(Match match) {
        if (match.getMatchOdds() != null) {
            for (MatchOdds odds : match.getMatchOdds()) {
                odds.setMatch(match);
            }
        }
        return matchRepository.save(match);
    }

    @Override
    @Transactional
    @CacheEvict(value = "matches", key = "#id")
    public Match update(Long id, Match matchToUpdate, boolean clearOdds) {
        Match existing = matchCacheService.geMatchById(id);
        existing.setDescription(matchToUpdate.getDescription());
        existing.setMatchDate(matchToUpdate.getMatchDate());
        existing.setMatchTime(matchToUpdate.getMatchTime());
        existing.setTeam_a(matchToUpdate.getTeam_a());
        existing.setTeam_b(matchToUpdate.getTeam_b());
        existing.setSport(matchToUpdate.getSport());

        if (clearOdds) {
            existing.getMatchOdds().clear();
        }
        if (matchToUpdate.getMatchOdds() != null) {
            matchToUpdate.getMatchOdds().forEach(odds -> {
                odds.setMatch(existing);
                existing.getMatchOdds().add(odds);
            });
        }
        return matchRepository.save(existing);
    }

    @Override
    @Transactional
    @CacheEvict(value = "matches", key = "#id")
    public void delete(Long id) {
        matchRepository.delete(matchCacheService.geMatchById(id));
    }
}

