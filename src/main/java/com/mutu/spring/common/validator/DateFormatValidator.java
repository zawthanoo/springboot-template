package com.mutu.spring.common.validator;

import java.text.SimpleDateFormat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<DateFormatConstraint, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try {
			format.parse(value);
		} catch (Exception e) {
			return false;
		} 
		return true;
	}
}