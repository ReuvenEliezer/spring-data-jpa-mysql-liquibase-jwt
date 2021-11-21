package com.liquibase.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DbConnectionProp {

    private String url;
    private String user;
    private String password;
    private String jdbcDriver;

}
