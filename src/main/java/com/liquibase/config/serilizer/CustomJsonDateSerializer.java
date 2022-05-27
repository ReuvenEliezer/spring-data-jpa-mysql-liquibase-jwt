package com.liquibase.utils.serilizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

public class CustomJsonDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String dateStr = com.liquibase.utils.serilizer.CustomJsonDateDeserializer.jasonDateFormat.format(date);
        jsonGenerator.writeString(dateStr);
    }
}
