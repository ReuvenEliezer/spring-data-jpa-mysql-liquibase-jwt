package com.liquibase.config;

import com.liquibase.services.audit.AuditTrailListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final Logger logger = LogManager.getLogger(DbConfig.class);


    @Autowired
    private DbConnectionProp dbConnectionProp;

    @Value("${spring.jpa.properties.hibernate.connection.url}")
    private String dbUrl;


    @Bean
    @Primary
    public DataSource dataSource() throws ClassNotFoundException {
        //use it to using a real db
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setUrl(dbConnectionProp.getUrl());
//        dataSource.setUsername(dbConnectionProp.getUsername());
//        dataSource.setPassword(dbConnectionProp.getPassword());
//
//        Class<? extends Driver> driverClass = (Class<? extends Driver>) Class.forName(dbConnectionProp.getDriverClassName());
//        dataSource.setDriverClass(driverClass);
//        return dataSource;
        logger.info("using url: {}", dbUrl);
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(dbUrl)
                .ignoreFailedDrops(true)
                .build();
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
