package com.example.matchapi.services;

import com.example.matchapi.entities.MatchOdds;
import java.util.List;

/**
 * Service interface for managing match odds.
 */
public interface MatchOddsService {

    /**
     * Retrieves all match odds.
     *
     * @return a list of all {@link MatchOdds}
     */
    List<MatchOdds> getAll();

    /**
     * Retrieves match odds by ID.
     *
     * @param id the ID of the match odds
     * @return the {@link MatchOdds} with the given ID
     */
    MatchOdds getById(Long id);

    /**
     * Creates new odds for a specific match.
     *
     * @param matchId the ID of the match
     * @param odds the {@link MatchOdds} to create
     * @return the created {@link MatchOdds}
     */
    MatchOdds create(Long matchId, MatchOdds odds);

    /**
     * Updates existing odds.
     *
     * @param id the ID of the odds to update
     * @param odds the updated {@link MatchOdds}
     * @return the updated {@link MatchOdds}
     */
    MatchOdds update(Long id, MatchOdds odds);

    /**
     * Deletes match odds by ID.
     *
     * @param id the ID of the odds to delete
     */
    void delete(Long id);
}
