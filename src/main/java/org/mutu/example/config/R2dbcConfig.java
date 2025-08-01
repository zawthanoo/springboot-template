package org.mutu.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;
import io.r2dbc.spi.ConnectionFactory;

/**
 * Structure for multiple db instance 
 */
@Configuration
public class R2dbcConfig {

    @Bean
    @ConfigurationProperties("database.primary.r2dbc")
    public R2dbcProperties primaryProperties() {
        return new R2dbcProperties();
    }
    
    @Bean
    public ConnectionFactory primaryConnectionFactory(R2dbcProperties primaryProperties) {
        return ConnectionFactoryBuilder
            .withUrl(primaryProperties.getUrl())
            .username(primaryProperties.getUsername())
            .password(primaryProperties.getPassword())
            .build();
    }

    @Bean("primaryTransactionManager")
    public ReactiveTransactionManager primaryTransactionManager(
        @Qualifier("primaryConnectionFactory") ConnectionFactory connectionFactory
    ) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean(name = "primaryDatabaseClient")
    public DatabaseClient primaryDatabaseClient(@Qualifier("primaryConnectionFactory") ConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }

    /*
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
        System.out.println(encoder.encode("user"));
    }
    */
}