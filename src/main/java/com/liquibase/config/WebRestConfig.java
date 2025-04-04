package com.liquibase.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class WebRestConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .connectTimeout(Duration.ofSeconds(2))
//                .readTimeout(Duration.ofSeconds(5))
//                .basicAuthentication("username", "password")
                .build();
    }


}

