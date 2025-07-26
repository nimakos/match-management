package com.example.matchapi.controllers;

import com.example.matchapi.entities.MatchOdds;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Match Odds", description = "Operations related to match odds")
public interface MatchOddsController {

    @Operation(summary = "Get all odds", description = "Returns a list of all match odds")
    ResponseEntity<List<MatchOdds>> getAllOdds();

    @Operation(summary = "Get odds by ID", description = "Returns specific odds by ID")
    @Parameter(name = "id", description = "ID of the odd to get")
    ResponseEntity<MatchOdds> getOdd(Long id);

    @Operation(summary = "Create odds", description = "Creates new odds for a match by giving the match id")
    @Parameter(name = "matchId", description = "ID of the match to create the odd")
    ResponseEntity<MatchOdds> createOdds(Long matchId, MatchOdds odds);

    @Operation(summary = "Update odds", description = "Updates match odds by ID")
    @Parameter(name = "id", description = "ID of the odd to update")
    ResponseEntity<MatchOdds> updateOdd(Long id, MatchOdds input);

    @Operation(summary = "Delete odds", description = "Deletes match odds by ID")
    @Parameter(name = "id", description = "ID of the odd to delete")
    ResponseEntity<Void> deleteOdd(Long id);
}
