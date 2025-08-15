package org.mutu.example.config.logging;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;


import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIdFilter implements WebFilter {
	
	@Value("${header-key-list.log4jThreadContextList}")
	private String log4jThreadContextList;
	
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    	ServerHttpRequest request = exchange.getRequest();
    	ServerHttpResponse response = exchange.getResponse();
    	
    	String correlationId = request.getHeaders().getFirst("x-root-id");
    	if(StringUtils.isEmpty(correlationId)) {
    		correlationId = UUID.randomUUID().toString(); 
    	}
    	Map<String, String> contextMap = new HashMap();
    	contextMap.put("x-root-id", correlationId);
    	
    	
    	if (!StringUtils.isEmpty(log4jThreadContextList)) {
			List<String> items = Arrays.asList(log4jThreadContextList.split("\\s*,\\s*"));
			for (String item : items) {
				if(!"x-root-id".equalsIgnoreCase(item)) {
					contextMap.put(item, request.getHeaders().getFirst(item));	
				}
			}
		}
    	response.getHeaders().add("x-root-id", correlationId);
        return chain.filter(exchange)
            .contextWrite(context -> {
                return context.put("log4j2.mdc", contextMap);
            })
            .doFinally(signalType -> {
                ThreadContext.clearMap();
            });
    }
}
