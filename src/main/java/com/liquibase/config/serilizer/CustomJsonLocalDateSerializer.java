package com.liquibase.config.serilizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomJsonLocalDateSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String dateStr = CustomJsonLocalDateDeserializer.localDateFormat.format(localDateTime);
        jsonGenerator.writeString(dateStr);
    }
}
