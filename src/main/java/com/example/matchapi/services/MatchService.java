package com.example.matchapi.services;

import com.example.matchapi.entities.Match;

import java.util.List;

public interface MatchService {
    /**
     * Retrieves all matches.
     * @return The List of Matches
     */
    List<Match> getAll();

    /**
     * Retrieves a match by its ID.
     * @param id The match id
     * @return The Match
     */
    Match getById(Long id);

    /**
     * Creates a new match.
     * @param match The match to create
     * @return The created Match
     */
    Match create(Match match);

    /**
     * Updates an existing match.
     * @param id The match id
     * @param match The match to update
     * @return The updated match
     */
    Match update(Long id, Match match);

    /**
     * Deletes a match by ID.
     * @param id The match id to delete
     */
    void delete(Long id);
}
