package org.mutu.example.dao;

import java.util.List;

import org.mutu.example.common.Utils;
import org.mutu.example.custommapper.EmployeeCustomMapper;
import org.mutu.example.dto.CreateEmployeeDtoReq;
import org.mutu.example.dto.EmployeeDto;
import org.mutu.example.zgen.entity.Employee;
import org.mutu.example.zgen.entity.EmployeeExample;
import org.mutu.example.zgen.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeDao {
	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private EmployeeCustomMapper employeeCustomMapper;

	public List<Employee> getEmployeeList() {
		EmployeeExample example = new EmployeeExample();
		return employeeMapper.selectByExample(example);
	}

	public EmployeeDto findByEmail(String email) {
		return employeeCustomMapper.findByEmail(email);
	}
	
	public void create(CreateEmployeeDtoReq dtoReq) {
		Employee employee = new Employee();
		employee.setName(dtoReq.getFullName());
		employee.setDob(Utils.getDate(dtoReq.getDob()));
		employee.setEmail(dtoReq.getEmail());
		employee.setMobile(dtoReq.getPhone());
		employee.setGender(dtoReq.getGender().toString());
		employeeMapper.insert(employee);
	}
}
