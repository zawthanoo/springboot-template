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
@ConfigurationProperties(prefix = "employee.datasource")
public class EmpDBConfig extends HikariConfig {
	
	@Bean(name = "employeeDataSource")
	public DataSource dataSource() throws SQLException {
		return new HikariDataSource(this);
	}

	@Bean(name = "employeeJdbcTemplate")
	public NamedParameterJdbcTemplate employeeDataSource(@Qualifier("employeeDataSource") DataSource employeeDataSource) {
		return new NamedParameterJdbcTemplate(employeeDataSource);
	}
	
	/*
	 * Remove this function if you don't need to initialize DB
	 */
	@Bean
	public DataSourceInitializer employeeDataSourceInitializer(@Qualifier("employeeDataSource") DataSource employeeDataSource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//		resourceDatabasePopulator.addScript(new ClassPathResource("employee_schema.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("employee_data.sql"));

		DataSourceInitializer mySqldataSourceInitializer = new DataSourceInitializer();
		mySqldataSourceInitializer.setDataSource(employeeDataSource);
		mySqldataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return mySqldataSourceInitializer;
	}
}
