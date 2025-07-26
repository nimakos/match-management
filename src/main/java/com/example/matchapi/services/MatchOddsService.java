package com.example.matchapi.services;

import com.example.matchapi.entities.MatchOdds;

import java.util.List;

public interface MatchOddsService {
    List<MatchOdds> getAll();
    MatchOdds getById(Long id);
    MatchOdds create(Long matchId, MatchOdds odds);
    MatchOdds update(Long id, MatchOdds odds);
    void delete(Long id);
}
