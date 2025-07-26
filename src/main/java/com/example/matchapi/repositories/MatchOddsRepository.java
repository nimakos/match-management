package com.example.matchapi.repositories;

import com.example.matchapi.entities.MatchOdds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchOddsRepository extends JpaRepository<MatchOdds, Long> {}