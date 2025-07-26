package com.example.matchapi.services;

import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.repositories.MatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @Override
    public Match getById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
    }

    @Override
    public Match create(Match match) {
        if (match.getMatchOdds() != null) {
            for (MatchOdds odds : match.getMatchOdds()) {
                odds.setMatch(match);
            }
        }
        return matchRepository.save(match);
    }

    @Override
    public Match update(Long id, Match matchToUpdate) {
        Match existing = getById(id);
        existing.setDescription(matchToUpdate.getDescription());
        existing.setMatchDate(matchToUpdate.getMatchDate());
        existing.setMatchTime(matchToUpdate.getMatchTime());
        existing.setTeam_a(matchToUpdate.getTeam_a());
        existing.setTeam_b(matchToUpdate.getTeam_b());
        existing.setSport(matchToUpdate.getSport());

        existing.getMatchOdds().clear();
        if (matchToUpdate.getMatchOdds() != null) {
            for (MatchOdds odds : matchToUpdate.getMatchOdds()) {
                odds.setMatch(existing);
                existing.getMatchOdds().add(odds);
            }
        }
        return matchRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        matchRepository.delete(getById(id));
    }
}

