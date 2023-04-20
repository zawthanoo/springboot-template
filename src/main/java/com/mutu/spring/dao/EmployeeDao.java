package com.mutu.spring.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mutu.spring.zgen.entity.Employee;
import com.mutu.spring.zgen.entity.EmployeeExample;
import com.mutu.spring.zgen.mapper.EmployeeMapper;


/**
 * @author Zaw Than Oo
 * @since 31-03-2023
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeDao {
	@Autowired
	private EmployeeMapper employeeMapper;
	
	public void insert(Employee employee) {
		employeeMapper.insert(employee);
	}
	
	public void delete(String empNo) {
		EmployeeExample example = new EmployeeExample();
		example.createCriteria().andEmpNoEqualTo(empNo);
		employeeMapper.deleteByExample(example);
	}
	
	public List<Employee> getList() {
		EmployeeExample example = new EmployeeExample();
		return employeeMapper.selectByExample(example);
	}
}
