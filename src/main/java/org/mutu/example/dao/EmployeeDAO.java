package org.mutu.example.dao;


import java.util.List;
import java.util.Map;

import org.mutu.example.config.exception.DAOException;
import org.mutu.example.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class EmployeeDAO {
	@Autowired
    private EmployeeSqlService sqlService;
	
	@Autowired
	@Qualifier("employeeJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public Employee findById(Long employeeId) {
		String query = sqlService.getSqlStatement(SQLQuery.EMPLOYEE_FIND_BY_ID);
		return sqlService.selectOne(query, Map.of("employeeId", employeeId), Employee.class).orElse(null);
	}
	
	public List<Employee> findByAge(Long fromAge, Long toAge) {
		String query = sqlService.getSqlStatement(SQLQuery.EMPLOYEE_FIND_BY_AGE);
		return sqlService.select(query, Map.of("fromAge", fromAge, "toAge", toAge), Employee.class);
	}
	
	public List<Employee> findAll() {
		String query = sqlService.getSqlStatement(SQLQuery.EMPLOYEE_FIND_ALL);
		return sqlService.select(query, null, Employee.class);
	}
	
	public List<Employee> daoExceptionTest() {
		if(true) {
			throw new DAOException("E001", "Test exception", null);
		}
		return null;
	}

	public void insert(Employee employee) {
		String query = sqlService.getSqlStatement(SQLQuery.EMPLOYEE_INSERT);
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(employee);
		jdbcTemplate.update(query, namedParameters);
	}
	
	public void update(Employee employee) {
		String query = sqlService.getSqlStatement(SQLQuery.EMPLOYEE_UPDATE);
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(employee);
		jdbcTemplate.update(query, namedParameters);
	}
}