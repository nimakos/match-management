package com.example.matchapi.controllers;

import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.services.MatchOddsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odds")
public class MatchOddsControllerImpl implements MatchOddsController {

    private final MatchOddsService oddsService;

    public MatchOddsControllerImpl(MatchOddsService oddsService) {
        this.oddsService = oddsService;
    }

    @GetMapping("/getAllOds")
    public ResponseEntity<List<MatchOdds>> getAllOdds() {
        return ResponseEntity.ok(oddsService.getAll());
    }

    @GetMapping("/getOdd/{id}")
    public ResponseEntity<MatchOdds> getOdd(@PathVariable Long id) {
        return ResponseEntity.ok(oddsService.getById(id));
    }

    @PostMapping("/createOdd")
    public ResponseEntity<MatchOdds> createOdds(@RequestParam Long matchId, @RequestBody MatchOdds odds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(oddsService.create(matchId, odds));
    }

    @PutMapping("/updateOdd/{id}")
    public ResponseEntity<MatchOdds> updateOdd(@PathVariable Long id, @RequestBody MatchOdds input) {
        return ResponseEntity.ok(oddsService.update(id, input));
    }

    @DeleteMapping("/deleteOdd/{id}")
    public ResponseEntity<Void> deleteOdd(@PathVariable Long id) {
        oddsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}