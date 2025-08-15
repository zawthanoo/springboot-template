package org.mutu.example.config.logging;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.mutu.example.common.MaskUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * @author Zaw Than Oo
 * @since 30-July-2025 <br/>
 *        This LoggingService to log and track for http request and response.
 *        ObjectMessage is used to support for log4j jsonlogger appender.
 */
@Component
@Log4j2
public class LoggingService {
    public void logRequest(String method, String uri, HttpHeaders headers, Object body) {
        List<LogHeader> logHeaders = buildHeadersMap(headers);
        LogHttpRequestEntity entity = new LogHttpRequestEntity();
        entity.setType("http-request");
        entity.setMethod(method);
        entity.setHeaders(logHeaders);
        entity.setPath(uri);
        entity.setBody(body);
        log.debug(new ObjectMessage(new LogMessage("Request", entity)));
    }

    public void logResponse(String method, String uri, String statusCode, HttpHeaders headers, Object body) {
        List<LogHeader> logHeaders = buildHeadersMap(headers);
        LogHttpResponseEntity entity = new LogHttpResponseEntity();
        entity.setType("http-response");
        entity.setMethod(method);
        entity.setHeaders(logHeaders);
        entity.setPath(uri);
        entity.setStatus(statusCode);
        entity.setBody(body);
        log.debug(new ObjectMessage(new LogMessage("Response", entity)));
    }

    private List<LogHeader> buildHeadersMap(HttpHeaders headers) {
        List<LogHeader> list = new ArrayList<LogHeader>();
        for (String headerName : headers.keySet()) {
        	if("authorization".equalsIgnoreCase(headerName)) {
                String value = headers.getFirst(headerName);
                list.add(new LogHeader(headerName, MaskUtil.maskToken(value, 15, 10)));
        	} else {
                String value = headers.getFirst(headerName);
                list.add(new LogHeader(headerName, value));
        	}
        }
        return list;
    }
}