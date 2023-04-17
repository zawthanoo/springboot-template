package com.mutu.spring.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mutu.spring.common.dto.ApiError;
import com.mutu.spring.common.dto.ApiStatus;
import com.mutu.spring.common.dto.InvalidField;
import com.mutu.spring.common.dto.MessageCode;
import com.mutu.spring.common.exception.BusinessLogicException;
import com.mutu.spring.common.exception.DAOException;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        It is used to hand an exception for the controller. <br/>
 *        Which is used to translate
 *        <code>Exception<code> to custom <code>ApiError</code>.<br/>
 *        If the system is thrown any exception or occurred error, this class
 *        automatically response <code>ApiError</code> object to client. <br/>
 * @modify 31-03-2023 <br/>
 * 		  Modify for springboot 3.0.
 */
@RestControllerAdvice
public class RestExceptionHandler  {

	@ExceptionHandler(BusinessLogicException.class)
	protected ResponseEntity<ApiError> handleBusinessLogicNotFound(BusinessLogicException ex) {
		ApiError apiError = new ApiError(ApiStatus.FAILED);
		apiError.setMessage(ex.getMessage());
		apiError.setMessageCode(ex.getErrorCode());
		apiError.setPayLoad(ex.getResponse());
		apiError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(DAOException.class)
	protected ResponseEntity<ApiError> handleDAOException(DAOException ex) {
		ApiError apiError = new ApiError(ApiStatus.FAILED);
		apiError.setMessage(ex.getMessage());
		apiError.setMessageCode(ex.getErrorCode());
		apiError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleInvalidArgument(MethodArgumentNotValidException ex) {
		List<InvalidField> invalidFieldList = new ArrayList<InvalidField>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			invalidFieldList.add(new InvalidField(error.getField(), error.getCode(), error.getDefaultMessage()));
		}
		ApiError apiError = new ApiError(ApiStatus.FAILED);
		apiError.setMessage("Request parameter is invalid.");
		apiError.setMessageCode(MessageCode.INVALID_REQUEST_PARAMETER);
		apiError.setPayLoad(invalidFieldList);
		apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
		return (ResponseEntity) buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiError> handle(Exception ex) {
		ApiError apiError = new ApiError(ApiStatus.FAILED);
		ex.printStackTrace();
		apiError.setMessage(ex.getMessage());
		apiError.setMessageCode(MessageCode.UNEXPECTED_ERROR);
		apiError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return buildResponseEntity(apiError);
	}
	
	/* for springboot 2.7.x
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<InvalidField> invalidFieldList = new ArrayList<InvalidField>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			invalidFieldList.add(new InvalidField(error.getField(), error.getCode(), error.getDefaultMessage()));
		}
		ApiError apiError = new ApiError(ApiStatus.FAILED);
		apiError.setMessage("Request parameter is invalid.");
		apiError.setMessageCode(MessageCode.INVALID_REQUEST_PARAMETER);
		apiError.setPayLoad(invalidFieldList);
		apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
		return (ResponseEntity) buildResponseEntity(apiError);
	}
	*/
	private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<ApiError>(apiError, apiError.getHttpStatus());
	}
}
