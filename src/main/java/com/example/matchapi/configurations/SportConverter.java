package com.example.matchapi.configurations;

import com.example.matchapi.enums.Sport;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SportConverter implements AttributeConverter<Sport, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Sport sport) {
        return sport != null ? sport.getCode() : null;
    }

    @Override
    public Sport convertToEntityAttribute(Integer code) {
        return code != null ? Sport.fromCode(code) : null;
    }
}

