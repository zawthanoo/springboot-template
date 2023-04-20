package com.mutu.spring.common.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


/**
 * @author Zaw Than Oo
 * @since 15-04-2023
 */
@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormatConstraint {
	String message() default "Invalid date.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}