package com.javadeveloperzone.liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
@EnableJpaAuditing
@ComponentScan(basePackages = {"dbconfig", "services"})
@EnableJpaRepositories(basePackages = {"repositories"})//, "ule", "controller.manager"})
@EntityScan("entities")
//@EnableJpaRepositories(basePackageClasses = .class)
public class LiquibaseApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LiquibaseApplication.class, args);
    }
}
