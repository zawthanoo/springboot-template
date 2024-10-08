package org.mutu.example.config.exception;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        This classed is used in DAO process when DAO/MyBatic Mapper have
 *        occurred any error or exception.
 */
public class DAOException extends RuntimeException {
	private static final long serialVersionUID = -1402323572092762148L;
	private String errorCode;
	private Object payload;

	public DAOException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public DAOException(String errorCode, String message, Throwable throwable, Object payload) {
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
