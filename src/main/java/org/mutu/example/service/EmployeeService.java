package org.mutu.example.service;

import java.util.List;

import org.mutu.example.config.exception.BusinessLogicException;
import org.mutu.example.dao.EmployeeDAO;
import org.mutu.example.dao.SQLQuery;
import org.mutu.example.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.gson.JsonObject;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeDAO employeeRepository;
	
	public Employee getById(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId);
		return employee;
	}
	/*
	public Employee add(Employee employee) {
		employeeRepository.save(employee);
		return employee;
	}

	public Employee update(Employee employee) {
		employeeRepository.save(employee);
		return employee;
	}
	
	public void delete(Long employeeId) {
		employeeRepository.deleteById(employeeId);
	}
	*/
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	public List<Employee> daoExceptionTest() {
		return employeeRepository.daoExceptionTest();
	}
	
	public void insert(Employee employee) {
		employeeRepository.insert(employee);
	}
	
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	public List<Employee> serviceExceptionTest() {
		if(true) {
			throw new BusinessLogicException("E001", "Test exception", null);
		}
		return null;
	}

	public List<Employee> runtimeExceptionTest() {
		if(true) {
			throw new RuntimeException("Testing for runtime exception");
		}
		return null;
	}
}
