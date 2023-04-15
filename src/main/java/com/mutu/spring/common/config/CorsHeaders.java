package com.mutu.spring.common.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zaw Than Oo
 * @since 29-03-2023
 */
@Configuration
@ConfigurationProperties("cors")
public class CorsHeaders {
	private List<String> headers;
	
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
}
