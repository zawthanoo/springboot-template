package org.mutu.example.custommapper;

import org.apache.ibatis.annotations.Mapper;
import org.mutu.example.dto.EmployeeDto;
/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018
 */

@Mapper
public interface EmployeeCustomMapper {
	public EmployeeDto findByEmail(String email);
}
