package org.mutu.example.api;

import java.util.List;

import org.mutu.example.dto.Department;
import org.mutu.example.dto.Employee;
import org.mutu.example.dto.StatusDto;
import org.mutu.example.service.DepartmentService;
import org.mutu.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author Zaw Than Oo
 * @since 14-04-2023
 */
@CrossOrigin(origins="*")
@RestController
@RequestMapping("api")
public class ApiController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
		Employee result = employeeService.getById(employeeId);
		return ResponseEntity.ok(result);
    }
	
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<StatusDto> creatEmployee(@Valid @RequestBody Employee employee) {
		employeeService.insert(employee);
		return ResponseEntity.ok(new StatusDto("OK"));
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ResponseEntity<StatusDto> updateEmployee(@Valid @RequestBody Employee employee) {
		employeeService.update(employee);
		return ResponseEntity.ok(new StatusDto("OK"));
	}
	
	@RequestMapping(value = "emplist", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployee() {
		List<Employee> result = employeeService.getAll();
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value = "daoExceptionTest", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> daoExceptionTest() {
		List<Employee> result = employeeService.daoExceptionTest();
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value = "serviceExceptionTest", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> serviceExceptionTest() {
		List<Employee> result = employeeService.serviceExceptionTest();
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value = "runtimeExceptionTest", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> runtimeExceptionTest() {
		List<Employee> result = employeeService.runtimeExceptionTest();
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value = "deplist", method = RequestMethod.GET)
	public ResponseEntity<List<Department>> getAllDepartment() {
		List<Department> result = departmentService.getAll();
		return ResponseEntity.ok(result);
	}
}
