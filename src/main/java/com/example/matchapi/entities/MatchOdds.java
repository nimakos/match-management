package com.example.matchapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class MatchOdds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "match_id", nullable = false)
    @JsonBackReference
    @Schema(type = "match", hidden = true)
    private Match match;

    @Schema(type = "string", example = "x")
    private String specifier;

    @Schema(type = "long", example = "2.5")
    private BigDecimal odd;

}