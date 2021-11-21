package com.liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan(basePackages = {
        "com.liquibase.config",
        "com.liquibase.controllers",
        "com.liquibase.services",
})
@EntityScan("com.liquibase.entities")
@EnableJpaRepositories(basePackages = {"com.liquibase.repositories"})
@EnableJpaAuditing
@SpringBootApplication
public class LiquibaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiquibaseApplication.class, args);
    }
}
