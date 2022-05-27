package com.liquibase.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class GeneralConfig {

    //https://stackoverflow.com/questions/70604058/objectmapper-enable-method-is-deprecated
//    @Bean
//    public ObjectMapper objectMapper() {
//        return JsonMapper.builder()
//                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
//                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
//                .build().findAndRegisterModules()
//                ;
//    }

    @Bean(name = {"objectMapper"})
    @Primary
    public static ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .failOnEmptyBeans(false)
                .failOnUnknownProperties(false)
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .featuresToDisable(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS)//WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

}
