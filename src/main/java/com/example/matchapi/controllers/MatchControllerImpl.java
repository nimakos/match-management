package com.example.matchapi.controllers;

import com.example.matchapi.entities.Match;
import com.example.matchapi.services.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.matchapi.mappings.Mappings.*;

@RestController
@RequestMapping(MATCHES)
public class MatchControllerImpl implements MatchController {

    private final MatchService matchService;

    public MatchControllerImpl(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping(GET_MATCHES)
    public ResponseEntity<List<Match>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAll());
    }

    @GetMapping(GET_MATCH)
    public ResponseEntity<Match> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getById(id));
    }

    @PostMapping(CREATE_MATCH)
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.create(match));
    }

    @PutMapping(UPDATE_MATCH)
    public ResponseEntity<Match> updateMatch(@PathVariable Long id, @RequestBody Match match, @RequestParam Boolean clearOdds) {
        return ResponseEntity.ok(matchService.update(id, match, clearOdds));
    }

    @DeleteMapping(DELETE_MATCH)
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
