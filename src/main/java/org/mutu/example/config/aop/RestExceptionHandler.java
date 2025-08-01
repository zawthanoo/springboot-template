package org.mutu.example.config.aop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.mutu.example.config.MessageCode;
import org.mutu.example.config.dto.ApiError;
import org.mutu.example.config.dto.ApiStatus;
import org.mutu.example.config.dto.InvalidField;
import org.mutu.example.config.exception.BusinessLogicException;
import org.mutu.example.config.exception.DAOException;
import org.mutu.example.config.logging.LogMessage;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        It is used to hand an exception for the controller. <br/>
 *        Which is used to translate
 *        <code>Exception<code> to custom <code>ApiError</code>.<br/>
 *        If the system is thrown any exception or occurred error, this class
 *        automatically response <code>ApiError</code> object to client. <br/>
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {
	
	private Logger logger = LogManager.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(BusinessLogicException.class)
	protected ResponseEntity<ApiError> handleBusinessLogicNotFound(BusinessLogicException ex) {

		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		logger.debug(new ObjectMessage(new LogMessage(errors.toString(), ex.getPayload())));

		ApiError apiError = new ApiError(ApiStatus.FAILED);
		apiError.setMessage(ex.getMessage());
		apiError.setMessageCode(ex.getErrorCode());
		apiError.setPayLoad(ex.getPayload());
		return buildResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DAOException.class)
	protected ResponseEntity<ApiError> handleDAOException(DAOException ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		logger.debug(new ObjectMessage(new LogMessage(errors.toString(), ex.getPayload())));

		ApiError apiError = new ApiError(ApiStatus.FAILED);
		apiError.setMessage(ex.getMessage());
		apiError.setMessageCode(ex.getErrorCode());
		apiError.setPayLoad(ex.getPayload());
		return buildResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		List<InvalidField> invalidFieldList = new ArrayList<InvalidField>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			invalidFieldList.add(new InvalidField(error.getField(), error.getCode(), error.getDefaultMessage()));
		}
		ApiError apiError = new ApiError(ApiStatus.FAILED);
		apiError.setMessage("Request parameter is invalid.");
		apiError.setMessageCode(MessageCode.INVALID_REQUEST_PARAMETER);
		apiError.setPayLoad(invalidFieldList);
		return (ResponseEntity) buildResponseEntity(apiError, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiError> handle(Exception ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		logger.debug(new ObjectMessage(new LogMessage(errors.toString(), ex.getMessage())));

		ApiError apiError = new ApiError(ApiStatus.FAILED);
		apiError.setMessage(ex.getMessage());
		apiError.setMessageCode(MessageCode.UNEXPECTED_ERROR);
		return buildResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError, HttpStatus httpStatus) {
		return new ResponseEntity<ApiError>(apiError, httpStatus);
	}
}
