package com.liquibase.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.liquibase.config.mappers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Configuration
public class GeneralConfig {

    //https://stackoverflow.com/questions/70604058/objectmapper-enable-method-is-deprecated
    @Bean
    public static ObjectMapper objectMapper() {
        return createObjectMapper();
    }

    private static ObjectMapper createObjectMapper() {
        return new ObjectMapper()
//                .findAndRegisterModules()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .registerModule(createCustomModule())
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                ;
    }

    private static SimpleModule createCustomModule() {
        SimpleModule module = new SimpleModule();

        module.addSerializer(LocalDateTime.class, new CustomJsonLocalDateSerializer());
        module.addDeserializer(LocalDateTime.class, new CustomJsonLocalDateDeserializer());

        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());

        module.addSerializer(Date.class, new CustomDateSerializer());
        module.addDeserializer(Date.class, new CustomDateDeserializer());

        module.addSerializer(Timestamp.class, new CustomTimestampSerializer());
        module.addDeserializer(Timestamp.class, new CustomTimestampDeserializer());

        module.addDeserializer(Duration.class, new DurationDeserializer());
        module.addSerializer(Duration.class, new DurationSerializer());

        return module;
    }


//    @Bean(name = {"objectMapper"})
//    @Primary
//    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
//        return builder
//                .serializationInclusion(JsonInclude.Include.NON_NULL)
//                .failOnEmptyBeans(false)
//                .failOnUnknownProperties(false)
//                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
//                .featuresToDisable(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS)//WRITE_DATES_AS_TIMESTAMPS)
//                .build();

    //    }
    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ISO_DATE.format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
            }

            @Override
            public String print(LocalDateTime object, Locale locale) {
                return DateTimeFormatter.ISO_DATE_TIME.format(object);
            }
        };
    }

    //    @Bean
//    public Formatter<Duration> DurationFormatter() {
//        return new Formatter<Duration>() {
//
//            @Override
//            public String print(Duration object, Locale locale) {
//                return Duration.from(object).toString();
//            }
//
//            @Override
//            public Duration parse(String text, Locale locale) throws ParseException {
//                return Duration.parse(text);
//            }
//        };
//    }

}
