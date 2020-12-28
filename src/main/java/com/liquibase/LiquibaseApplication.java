package com.liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableJpaAuditing
@ComponentScan(basePackages = {"com/liquibase/dbconfig", "com/liquibase/services"})
@EnableJpaRepositories(basePackages = {"com/liquibase/repositories"})//, "ule", "controller.manager"})
@EntityScan("com/liquibase/entities")
//@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = .class)
public class LiquibaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiquibaseApplication.class, args);
    }
}
