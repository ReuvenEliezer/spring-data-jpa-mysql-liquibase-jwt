package com.liquibase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

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
        dataSource.setUsername(dbConnectionProp.getUser());
        dataSource.setPassword(dbConnectionProp.getPassword());

        Class<? extends Driver> driverClass = (Class<? extends Driver>) Class.forName(dbConnectionProp.getDriverClassName());
        dataSource.setDriverClass(driverClass);
        return dataSource;
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
