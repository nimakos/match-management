package com.example.matchapi.controllers;

import com.example.matchapi.entities.Match;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Matches", description = "Operations related to matches")
public interface MatchController {
    @Operation(summary = "Get all matches", description = "Returns a list of all matches")
    @GetMapping
    ResponseEntity<List<Match>> getAllMatches();

    @Operation(summary = "Get match by ID", description = "Returns a specific match by ID")
    @Parameter(name = "id", description = "ID of the match to get")
    ResponseEntity<Match> getMatch(Long id);

    @Operation(summary = "Create a match", description = "Creates a new match")
    ResponseEntity<Match> createMatch(Match match);

    @Operation(summary = "Update match", description = "Updates a match by ID, and parameter to clear or not the odds")
    @Parameter(name = "id", description = "ID of the match to update")
    @Parameter(name = "clearOdds", description = "Whether to clear the existing odds (true = clear, false = keep)")
    ResponseEntity<Match> updateMatch(Long id, Match match, Boolean clearOdds);

    @Operation(summary = "Delete match", description = "Deletes a match by ID")
    @Parameter(name = "id", description = "ID of the match to delete")
    ResponseEntity<Void> deleteMatch(Long id);
}
