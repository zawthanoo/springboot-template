package org.mutu.example.config.aop;

import java.time.LocalDateTime;

import org.mutu.example.config.MessageCode;
import org.mutu.example.config.dto.ApiError;
import org.mutu.example.config.dto.ApiStatus;
import org.mutu.example.config.dto.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        It is like a intercepter class. Which is used to make customize
 *        response object for client response.<br/>
 *        If the system is thrown any exception or occurred error, this class
 *        automatically response <code>ApiError</code> object to client.<br/>
 *        Otherwise, this class automatically response <code>Result</code>
 *        object to client.<br/>
 */
@ControllerAdvice
public class ResponseEntityHandler implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(final MethodParameter returnType,
			final Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(final Object body, final MethodParameter returnType,
			final MediaType selectedContentType, final Class<? extends HttpMessageConverter<?>> selectedConverterType,
			final ServerHttpRequest request, final ServerHttpResponse response) {
		response.setStatusCode(HttpStatus.OK);
		if (body instanceof RuntimeException) {
			RuntimeException re = (RuntimeException) body;
			ApiError apiError = new ApiError(ApiStatus.FAILED);
			apiError.setMessage(re.getMessage());
			apiError.setMessageCode(MessageCode.UNEXPECTED_ERROR);
			apiError.setTimestamp(LocalDateTime.now());
			return apiError;
		}
		if (body instanceof ApiError) {
			return body;
		}
		if (body instanceof String) {
			return body;
		}
		if (returnType.getParameterType().isAssignableFrom(void.class)) {
			Result result = new Result();
			result.setData(body);
			result.setStatus(ApiStatus.SUCCESS);
			return result;
		}
		Result result = new Result();
		result.setData(body);
		result.setStatus(ApiStatus.SUCCESS);
		return result;
	}
}