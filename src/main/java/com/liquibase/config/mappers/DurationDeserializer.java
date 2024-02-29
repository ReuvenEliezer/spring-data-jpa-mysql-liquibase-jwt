package com.liquibase.config.mappers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;


public class DurationDeserializer extends StdDeserializer<Duration> {

    public DurationDeserializer() {
        this(null);
    }

    public DurationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Duration deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        Integer hour;
        Integer min;
        Integer sec;
//        Integer milliSec;
//        Integer nanoSec;
        try {
            hour = (Integer) (node.get("hour")).numberValue();
        } catch (Exception e) {
            hour = 0;
        }
        try {
            min = (Integer) (node.get("min")).numberValue();
        } catch (Exception e) {
            min = 0;

        }
        try {
            sec = (Integer) (node.get("sec")).numberValue();
        } catch (Exception e) {
            sec = 0;

        }
//        try {
//            milliSec = (Integer) ((IntNode) node.get("milliSec")).numberValue();
//        } catch (Exception e) {
//            milliSec = 0;
//
//        }
//        try {
//            nanoSec = (Integer) ((IntNode) node.get("nanoSec")).numberValue();
//        } catch (Exception e) {
//            nanoSec = 0;
//
//        }

        if (hour == null && min == null && sec == null /*&& milliSec == null && nanoSec == null*/) return Duration.ZERO;

        if (hour == null)
            hour = 0;
        if (min == null)
            min = 0;
        if (sec == null)
            sec = 0;
//        if (milliSec == null)
//            milliSec = 0;
//        if (nanoSec == null)
//            nanoSec = 0;

        Duration result = Duration.ofHours(hour)
                .plusMinutes(min)
                .plusSeconds(sec)
//                .plusMillis(milliSec)
//                .plusNanos(nanoSec)
                ;

        return result;
    }


}
