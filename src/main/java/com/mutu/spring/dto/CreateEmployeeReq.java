package com.mutu.spring.dto;

import com.mutu.spring.common.validator.DateFormatConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeReq {
	@NotBlank
    private String empNo;
	
	@NotBlank
    private String firstName;
	
	@NotBlank
    private String lastName;
    
    @DateFormatConstraint
    private String dob;
    
    @Email
    private String email;
}