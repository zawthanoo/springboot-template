package com.mutu.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mutu.spring.dao.EmployeeDao;
import com.mutu.spring.dto.CreateEmployeeReq;
import com.mutu.spring.dto.DeleteEmpReq;
import com.mutu.spring.zgen.entity.Employee;

/**
 * @author Zaw Than Oo
 * @since 31-03-2023
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	
	public void creatEmployee(CreateEmployeeReq createReq) {
		Employee employee = new Employee();
		employee.setEmpNo(createReq.getEmpNo());
		employee.setFirstName(createReq.getFirstName());
		employee.setLastName(createReq.getLastName());
		employee.setEmail(createReq.getEmail());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dob = format.parse(createReq.getDob());
			employee.setDob(dob);
		} catch (ParseException e) {
		}
		employeeDao.insert(employee);
	}
	
	public void deleteEmployee(DeleteEmpReq deleteEmpReq) {
		employeeDao.delete(deleteEmpReq.getEmpNo());
	}
	
	public List<Employee> getList() {
		return employeeDao.getList();
	}
}
