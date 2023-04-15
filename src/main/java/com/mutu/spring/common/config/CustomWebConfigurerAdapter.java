package com.mutu.spring.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 * @update 15-04-2023 <br/> springboot-3.0.x       
 */

@Component
public class CustomWebConfigurerAdapter implements WebMvcConfigurer {
	
   @Autowired
   private CustomLogInterceptor httpServiceInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(httpServiceInterceptor);
   }
}
