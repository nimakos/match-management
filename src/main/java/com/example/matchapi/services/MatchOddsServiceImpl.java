package com.example.matchapi.services;

import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.repositories.MatchOddsRepository;
import com.example.matchapi.repositories.MatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatchOddsServiceImpl implements MatchOddsService {

    private final MatchOddsRepository oddsRepo;
    private final MatchRepository matchRepo;

    public MatchOddsServiceImpl(MatchOddsRepository oddsRepo, MatchRepository matchRepo) {
        this.oddsRepo = oddsRepo;
        this.matchRepo = matchRepo;
    }

    @Override
    public List<MatchOdds> getAll() {
        return oddsRepo.findAll();
    }

    @Override
    public MatchOdds getById(Long id) {
        return oddsRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Odds not found"));
    }

    @Override
    public MatchOdds create(Long matchId, MatchOdds odds) {
        Match match = matchRepo.findById(matchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
        odds.setMatch(match);
        return oddsRepo.save(odds);
    }

    @Override
    public MatchOdds update(Long id, MatchOdds input) {
        MatchOdds existing = getById(id);

        if (input.getMatch() != null && !existing.getMatch().getId().equals(input.getMatch().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change associated Match");
        }

        existing.setOdd(input.getOdd());
        existing.setSpecifier(input.getSpecifier());
        return oddsRepo.save(existing);
    }

    @Override
    public void delete(Long id) {
        MatchOdds odds = getById(id);
        oddsRepo.delete(odds);
    }
}

