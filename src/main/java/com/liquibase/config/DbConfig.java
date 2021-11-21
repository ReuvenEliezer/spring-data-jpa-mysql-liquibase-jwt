package com.liquibase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Autowired
    private DbConnectionProp dbConnectionProp;

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbConnectionProp.getUrl());
        dataSourceBuilder.username(dbConnectionProp.getUser());
        dataSourceBuilder.password(dbConnectionProp.getPassword());
        dataSourceBuilder.driverClassName(dbConnectionProp.getJdbcDriver());
        return dataSourceBuilder.build();
    }

//    @Primary
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        return createEntityManagerFactory(dataSource(), createAdditionalProperties(), "primaryPersistUnit");
//    }

//    @Bean
//    public DataSource dataSource() {
//        return createDataSource(connectionURL, userName, dbPassword, mySqlDriverName);
//    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
