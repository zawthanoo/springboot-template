package org.mutu.example.config.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        This classed is used as response entity to the client when the system
 *        throw an exception.
 */

@Data
public class ApiError {
	private String status;
	private String messageCode;
	private String message;
	private Object payLoad;

	private ApiError() {
	}

	public ApiError(String status) {
		this();
		this.status = status;
	}

	public ApiError(String status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
	}

	public ApiError(String status, String message, String messageCode, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
	}
}