package org.mutu.example.service;

import java.util.List;

import org.mutu.example.dao.EmployeeRepository;
import org.mutu.example.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Employee getById(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElse(null);
		return employee;
	}

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
	
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
}
