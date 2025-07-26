package com.example.matchapi.enums;

import com.example.matchapi.configurations.SportDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

@Getter

@JsonDeserialize(using = SportDeserializer.class)
public enum Sport {
    FOOTBALL(1),
    BASKETBALL(2);

    private final int code;

    Sport(int code) {
        this.code = code;
    }

    public static Sport fromCode(int code) {
        for (Sport sport : Sport.values()) {
            if (sport.getCode() == code) {
                return sport;
            }
        }
        throw new IllegalArgumentException("Invalid Sport code: " + code);
    }
}
