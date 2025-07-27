package com.example.matchapi.configurations;

import com.example.matchapi.enums.Sport;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SportSerializer extends JsonSerializer<Sport> {
    @Override
    public void serialize(Sport sport, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(sport.getCode());
    }
}
