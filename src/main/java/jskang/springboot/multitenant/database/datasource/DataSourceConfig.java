package jskang.springboot.multitenant.database.datasource;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import jskang.springboot.multitenant.database.DatabaseEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final Environment env;

    public String getEnv(String key) {
        return env.getProperty(key);
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSourceRouting dataSourceRouting = new DataSourceRouting();
        dataSourceRouting.setTargetDataSources(targetDataSources());
        dataSourceRouting.setDefaultTargetDataSource(companyADataSource());
        return dataSourceRouting;
    }

    private Map<Object, Object> targetDataSources() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseEnum.companyA, companyADataSource());
        targetDataSources.put(DatabaseEnum.companyB, companyBDataSource());
        return targetDataSources;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan("kr.co.wisenut.multitenant");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        factory.setJpaVendorAdapter(vendorAdapter);

        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    @ConfigurationProperties("datasource.company-a")
    public DataSourceProperties aaaDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource companyADataSource() {
        return aaaDataSourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean
    @ConfigurationProperties("datasource.company-b")
    public DataSourceProperties japanDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource companyBDataSource() {
        return japanDataSourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
    }
}