package com.liquibase.dbconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
//@EnableJpaRepositories(basePackages = {"dao"})//, "controller.module", "manager"})
public class DbConfig {

    @Value("${connectionURL}")
    private String connectionURL;

    @Value("${dbUserName}")
    private String userName;

    @Value("${dbPassword}")
    private String dbPassword;

    @Value("${jdbcdriver}")
    private String jdbcdriver;

    @Primary
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(connectionURL);
        dataSourceBuilder.username(userName);
        dataSourceBuilder.password(dbPassword);
        dataSourceBuilder.driverClassName(jdbcdriver);
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
