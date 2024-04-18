package org.mutu.example.config.logging;


import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        This Interceptor to log for GET Http Request.
 */
@Component
public class CustomLogInterceptor implements HandlerInterceptor {
	@Value("${header-key-list.log4jThreadContextList}")
	private String log4jThreadContextList;
    @Autowired
    private LoggingService loggingService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    	if(!StringUtils.isEmpty(log4jThreadContextList)) {
    		List<String> items = Arrays.asList(log4jThreadContextList.split("\\s*,\\s*"));
    		for(String item : items) {
    			ThreadContext.put(item, request.getHeader(item));	
    		}
    	}
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
            loggingService.logRequest(request, null);
        }
        return true;
    }
}
