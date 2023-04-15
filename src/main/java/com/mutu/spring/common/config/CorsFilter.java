package com.mutu.spring.common.config;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Zaw Than Oo
 * @since 29-03-2023
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {

	@Autowired
	private CorsHeaders corsHeaders;
	
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		for(String header : corsHeaders.getHeaders()) {
            int index = header.indexOf(":");
            if (index > -1) {
                String key = header.substring(0, index);
                String value = header.substring(index + 2, header.length());
                response.setHeader(key, value);
            }
		}
		
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		doFilter(request, response, filterChain);
		
	}
}	