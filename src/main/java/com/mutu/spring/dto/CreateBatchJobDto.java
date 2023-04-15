package com.mutu.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBatchJobDto {
	private String jobName;
	private String cronExp;
	private String curl;
}
