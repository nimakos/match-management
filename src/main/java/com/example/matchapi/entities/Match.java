package com.example.matchapi.entities;

import com.example.matchapi.enums.Sport;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(type = "string", example = "PAO-OSFP")
    private String description;

    @Schema(type = "string", example = "2025-07-24")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate matchDate;

    @Schema(type = "string", example = "12:30:00")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime matchTime;

    @Schema(type = "string", example = "PAO")
    private String team_a;

    @Schema(type = "string", example = "OSFP")
    private String team_b;

    @Enumerated(EnumType.STRING)
    private Sport sport;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "List of odds")
    private List<MatchOdds> matchOdds  = new ArrayList<>();

}