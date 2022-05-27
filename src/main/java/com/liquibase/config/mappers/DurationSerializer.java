package com.liquibase.config.mappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;

/**
 * Created by dudim on 30/01/2017.
 */
public class DurationSerializer  extends StdSerializer<Duration> {


        public DurationSerializer() {
            this(null);
        }

        public DurationSerializer(Class<Duration> t) {
            super(t);
        }

        @Override
        public void serialize(
                Duration value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException, JsonProcessingException {

            if (value == null)
                value = Duration.ZERO;

//            int nanos = value.getNano();
            long seconds = value.getSeconds();
            long absSeconds = Math.abs(seconds);
//            long absNanos = Math.abs(nanos);


            jgen.writeStartObject();
            jgen.writeNumberField("hour",   absSeconds / 3600);
            jgen.writeNumberField("min",  (absSeconds % 3600) / 60);
            jgen.writeNumberField("sec",   absSeconds % 60);
//            jgen.writeNumberField("milliSec",   absNanos / 1000000);
//            jgen.writeNumberField("nanoSec",   absNanos % 1000000);
            jgen.writeEndObject();
        }





}
