package com.example.matchapi.configurations;

import com.example.matchapi.enums.Sport;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class SportDeserializer extends JsonDeserializer<Sport> {
    @Override
    public Sport deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        int code = Integer.parseInt(p.getText());
        return Sport.fromCode(code);
    }
}
