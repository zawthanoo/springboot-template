package org.mutu.example.custommapper;

import org.apache.ibatis.annotations.Mapper;
import org.mutu.example.dto.LoginUser;
/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018
 */

@Mapper
public interface SecurityMapper {
	/**
	 * For Spring Security
	 */
	public LoginUser loadUserByUsername(String username);
}