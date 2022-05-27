package com.liquibase.utils.serilizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

    public static final DateFormat jasonDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {

        //DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
        String dateStr = jsonparser.getText();
        try {
            return jasonDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}
