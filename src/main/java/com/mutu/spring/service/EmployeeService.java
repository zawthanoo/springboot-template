package com.mutu.spring.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.springframework.stereotype.Service;

import com.mutu.spring.common.dto.LogMessage;
import com.mutu.spring.dto.CreateEmployeeReq;


/**
 * @author Zaw Than Oo
 * @since 14-04-2023
 */
@Service
public class EmployeeService {
	Logger logger = LogManager.getLogger(EmployeeService.class);
	
	public void creatEmployee(CreateEmployeeReq createReq) {
		logger.debug(new ObjectMessage(new LogMessage("New employee is created", createReq)));
	}
}
