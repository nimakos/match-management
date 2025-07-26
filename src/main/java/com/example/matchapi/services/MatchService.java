package com.example.matchapi.services;

import com.example.matchapi.entities.Match;

import java.util.List;

public interface MatchService {
    List<Match> getAll();
    Match getById(Long id);
    Match create(Match match);
    Match update(Long id, Match match);
    void delete(Long id);
}
