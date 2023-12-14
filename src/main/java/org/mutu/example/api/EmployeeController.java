package org.mutu.example.api;

import java.util.List;

import javax.validation.Valid;

import org.mutu.example.dto.CreateEmployeeDtoReq;
import org.mutu.example.dto.EmployeeDto;
import org.mutu.example.services.EmployeeService;
import org.mutu.example.zgen.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping()
	public String greeting() {
		return "Spring-Rest API is tarted.....";
	}

	@RequestMapping("/list")
	public List<Employee> getEmployeeList() {
		return employeeService.getEmployeeList();
	}

	@RequestMapping("/search/{email}")
	public EmployeeDto getByEmail(@PathVariable("email") String email) {
		return employeeService.findByEmail(email);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void create(@Valid @RequestBody CreateEmployeeDtoReq employeeCreate) {
		employeeService.create(employeeCreate);
	}
}
