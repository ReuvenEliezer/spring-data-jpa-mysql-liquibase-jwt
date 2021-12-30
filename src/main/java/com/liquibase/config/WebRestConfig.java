package com.liquibase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebRestConfig {

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
//        return restTemplateBuilder
//                .basicAuthentication("username", "password")
//                .build();
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

