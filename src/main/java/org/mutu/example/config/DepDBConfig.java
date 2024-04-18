package org.mutu.example.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ConfigurationProperties(prefix = "department.datasource")
public class DepDBConfig extends HikariConfig {
	
	@Bean(name = "departmentDataSource")
	public DataSource dataSource() throws SQLException {
		return new HikariDataSource(this);
	}

	@Bean(name = "departmentJdbcTemplate")
	public NamedParameterJdbcTemplate departmentDataSource(@Qualifier("departmentDataSource") DataSource departmentDataSource) {
		return new NamedParameterJdbcTemplate(departmentDataSource);
	}
	
	/*
	 * Remove this function if you don't need to initialize DB
	 */
	@Bean
	public DataSourceInitializer departmentDataSourceInitializer(@Qualifier("departmentDataSource") DataSource departmentDataSource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//		resourceDatabasePopulator.addScript(new ClassPathResource("department_schema.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("department_data.sql"));

		DataSourceInitializer mySqldataSourceInitializer = new DataSourceInitializer();
		mySqldataSourceInitializer.setDataSource(departmentDataSource);
		mySqldataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return mySqldataSourceInitializer;
	}	
}
