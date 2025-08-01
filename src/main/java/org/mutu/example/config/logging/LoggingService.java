package org.mutu.example.config.logging;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
/**
 * @author Zaw Than Oo
 * @since 30-July-2025 <br/>
 *        This LoggingService to log and track for http request and response.
 *        ObjectMessage is used to support for log4j jsonlogger appender.
 */
@Component
public class LoggingService {
	private static final Logger logger = LogManager.getLogger(LoggingService.class);

	public void logRequest(String method, String uri, HttpHeaders headers, String body) {
		List<LogHeader> logHeaders = buildHeadersMap(headers);
		LogHttpRequestEntity entity = new LogHttpRequestEntity();
		entity.setType("http-request");
		entity.setMethod(method);
		entity.setHeaders(logHeaders);
		entity.setPath(uri);
		entity.setBody(body);
		logger.debug(new ObjectMessage(new LogMessage("Request", entity)));
	}

	public void logResponse(String method, String uri, String statusCode, HttpHeaders headers, String body) {
		List<LogHeader> logHeaders = buildHeadersMap(headers);
		LogHttpResponseEntity entity = new LogHttpResponseEntity();
		entity.setType("http-response");
		entity.setMethod(method);
		entity.setHeaders(logHeaders);
		entity.setPath(uri);
		entity.setStatus(statusCode);
		entity.setBody(body);
		logger.debug(new ObjectMessage(new LogMessage("Response", entity)));
	}

	private List<LogHeader> buildHeadersMap(HttpHeaders headers) {
		List<LogHeader> list = new ArrayList<LogHeader>();
		for (String headerName : headers.keySet()) {
			String value = headers.getFirst(headerName);
			list.add(new LogHeader(headerName, value));
		}
		return list;
	}
}