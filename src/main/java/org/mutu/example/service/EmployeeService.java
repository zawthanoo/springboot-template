package org.mutu.example.service;

import java.util.List;

import org.mutu.example.config.exception.BusinessLogicException;
import org.mutu.example.dao.EmployeeDAO;
import org.mutu.example.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeDAO employeeDAO;
	
	public Employee getById(Long employeeId) {
		Employee employee = employeeDAO.findById(employeeId);
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
	*/
	
	public void delete(int employeeId) {
		employeeDAO.delete(employeeId);
	}

	public List<Employee> getAll() {
		return employeeDAO.findAll();
	}
	
	public List<Employee> daoExceptionTest() {
		return employeeDAO.daoExceptionTest();
	}
	
	public void insert(Employee employee) {
		employeeDAO.insert(employee);
	}
	
	public void update(Employee employee) {
		employeeDAO.update(employee);
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
