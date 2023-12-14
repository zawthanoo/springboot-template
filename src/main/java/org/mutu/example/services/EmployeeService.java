package org.mutu.example.services;

import java.util.List;

import org.mutu.example.dao.EmployeeDao;
import org.mutu.example.dto.CreateEmployeeDtoReq;
import org.mutu.example.dto.EmployeeDto;
import org.mutu.example.zgen.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	public List<Employee> getEmployeeList() {
		return employeeDao.getEmployeeList();
	}

	public EmployeeDto findByEmail(String email) {
		return employeeDao.findByEmail(email);
	}
	
	public void create(CreateEmployeeDtoReq dtoReq) {
		employeeDao.create(dtoReq);
	}
}
