package com.liquibase.config.serilizer.mappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalTime;

public class LocalTimeSerializer extends StdSerializer<LocalTime> {


    public LocalTimeSerializer() {
        this(null);
    }

    public LocalTimeSerializer(Class<LocalTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        if (value == null)
            value = LocalTime.MIDNIGHT;

        jgen.writeRawValue("\"" +value.toString() + "\"");
    }


}
