package com.liquibase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Driver;


@Configuration
public class DbConfig {

    @Autowired
    private DbConnectionProp dbConnectionProp;


    @Bean
    @Primary
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl(dbConnectionProp.getUrl());
        dataSource.setUsername(dbConnectionProp.getUsername());
        dataSource.setPassword(dbConnectionProp.getPassword());

        Class<? extends Driver> driverClass = (Class<? extends Driver>) Class.forName(dbConnectionProp.getDriverClassName());
        dataSource.setDriverClass(driverClass);
        return dataSource;

//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .setName("jdbc:h2:mem:netapp;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS netapp")
//                .ignoreFailedDrops(true)
//                .build();
    }


//    @Primary
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        return createEntityManagerFactory(dataSource(), createAdditionalProperties(), "primaryPersistUnit");
//    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
