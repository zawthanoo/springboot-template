package org.mutu.example.dao;


import java.util.List;

import org.mutu.example.dto.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class DepartmentDAO {
	@Autowired
    private DepartmentSqlService sqlService;
	
	public List<Department> findAll() {
		String query = sqlService.getSqlStatement(SQLQuery.DEPARTMENT_FIND_ALL);
		return sqlService.select(query, null, Department.class);
	}

}