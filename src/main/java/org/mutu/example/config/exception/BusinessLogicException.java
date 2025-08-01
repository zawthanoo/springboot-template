package org.mutu.example.config.exception;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        This classed is used to throw exception for business logic.
 */
public class BusinessLogicException extends RuntimeException {
	private static final long serialVersionUID = -4636343176401289427L;
	private String errorCode;
	private Object payload;

	public BusinessLogicException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessLogicException(String errorCode, String message, Object payload) {
		super(message);
		this.payload = payload;
		this.errorCode = errorCode;
	}

	public BusinessLogicException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}
	
	public BusinessLogicException(String errorCode, String message,  Object payload, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
		this.payload = payload;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Object getPayload() {
		return payload;
	}
}
