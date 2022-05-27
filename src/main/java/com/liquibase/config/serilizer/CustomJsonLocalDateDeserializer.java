package com.liquibase.config.serilizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomJsonLocalDateDeserializer extends JsonDeserializer<LocalDateTime> {

//    private static final DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    protected static final DateTimeFormatter localDateFormat = DateTimeFormatter.ISO_LOCAL_DATE;


    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            String dateStr = jsonParser.getText();
            return LocalDateTime.parse(dateStr, localDateFormat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
