package com.mutu.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mutu.spring.dto.CreateEmployeeReq;
import com.mutu.spring.service.EmployeeService;

import jakarta.validation.Valid;

/**
 * @author Zaw Than Oo
 * @since 14-04-2023
 */

@RestController
public class ApiController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/create-emp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> creatEmployee(@Valid @RequestBody CreateEmployeeReq employee) {
		employeeService.creatEmployee(employee);
		return ResponseEntity.ok("success");
	}
}
