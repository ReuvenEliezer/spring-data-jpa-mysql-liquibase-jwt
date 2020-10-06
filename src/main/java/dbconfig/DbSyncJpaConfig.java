//package dbconfig;
//
//import org.apache.tomcat.jdbc.pool.DataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.persistence.EntityManagerFactory;
//

//@Configuration
//@PropertySource("classpath:db.properties")
//public class DbSyncJpaConfig {
//
//    @Value("${dbsync.connectionURL}")
//    private String connectionURL;
//
//    @Value("${dbsync.dbUserName}")
//    private String userName;
//
//    @Value("${dbsync.dbPassword}")
//    private String dbPassword;
//
//    @Bean(name = dbsyncEntityManagerFactory)
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        return createEntityManagerFactory(dataSource(), createAdditionalProperties(), dbsyncPersistUnit);
//    }
//
////    @Bean(name = dbsyncDataSource)
////    public DataSource dataSource() {
////        return createDataSource(connectionURL, userName, dbPassword, mySqlDriverName);
////    }
//
////    @Bean(name = dbsyncTransactionManager)
////    public PlatformTransactionManager transactionManager(@Qualifier(dbsyncEntityManagerFactory) EntityManagerFactory emf) {
////        return createTransactionManager(emf);
////    }
//
////    @Bean(name = dbsyncJdbcTemplate)
////    public JdbcTemplate jdbcTemplate(@Qualifier(dbsyncDataSource) DataSource dataSource) {
////        return new JdbcTemplate(dataSource);
////    }
//
//}
