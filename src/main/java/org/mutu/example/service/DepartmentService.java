package org.mutu.example.service;

import java.util.List;

import org.mutu.example.dao.DepartmentDAO;
import org.mutu.example.dto.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentDAO departmentRepository;
	
	public List<Department> getAll() {
		return departmentRepository.findAll();
	}
}
