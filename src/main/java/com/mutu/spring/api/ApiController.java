package com.mutu.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mutu.spring.dto.CreateBatchJobDto;
import com.mutu.spring.job.ScheduleTaskService;

import jakarta.validation.Valid;

/**
 * @author Zaw Than Oo
 * @since 14-04-2023
 */

@RestController
public class ApiController {

	@Autowired
	private ScheduleTaskService acheduleTaskService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> creatEmployee(@Valid @RequestBody CreateBatchJobDto createBatchJobDto) {
		acheduleTaskService.addJob(createBatchJobDto);
		return ResponseEntity.ok("success");
	}
}
