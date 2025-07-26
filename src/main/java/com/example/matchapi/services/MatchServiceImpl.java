package com.example.matchapi.services;

import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.exceptions.ObjectNotFoundException;
import com.example.matchapi.repositories.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new ObjectNotFoundException("Match with id: " + id + " not found"));
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
    public Match update(Long id, Match matchToUpdate, boolean clearOdds) {
        Match existing = getById(id);
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
    public void delete(Long id) {
        matchRepository.delete(getById(id));
    }
}

