package com.liquibase.config.mappers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalTime;

public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

    public LocalTimeDeserializer() {
        this(null);
    }

    public LocalTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        if (node == null || node.toString() == null) {
            return LocalTime.MIDNIGHT;
        }

        String text = node.toString();
        text = text.replace("\"", "");
        String[] values = text.split(":");


        Integer hour;
        Integer min;
        Integer sec;
        try {
            hour = Integer.parseInt(values[0]);
        } catch (Exception e) {
            hour = 0;
        }
        try {
            min = Integer.parseInt(values[1]);
        } catch (Exception e) {
            min = 0;

        }
        try {
            sec = Integer.parseInt(values[2]);
        } catch (Exception e) {
            sec = 0;

        }

        if (hour == null && min == null && sec == null) return LocalTime.MIDNIGHT;

        if (hour == null)
            hour = 0;
        if (min == null)
            min = 0;
        if (sec == null)
            sec = 0;

        LocalTime result = LocalTime.of(hour, min, sec);

        return result;
    }


}
