package com.example.matchapi.controllers;

import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.services.MatchOddsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odds")
public class MatchOddsController {

    private final MatchOddsService oddsService;

    public MatchOddsController(MatchOddsService oddsService) {
        this.oddsService = oddsService;
    }

    @GetMapping
    public List<MatchOdds> all() {
        return oddsService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchOdds> getOdds(@PathVariable Long id) {
        return ResponseEntity.ok(oddsService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MatchOdds> createOdds(@RequestParam Long matchId, @RequestBody MatchOdds odds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(oddsService.create(matchId, odds));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchOdds> updateOdds(@PathVariable Long id, @RequestBody MatchOdds input) {
        return ResponseEntity.ok(oddsService.update(id, input));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOdds(@PathVariable Long id) {
        oddsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}