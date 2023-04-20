package com.mutu.spring;

import java.util.TimeZone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import jakarta.annotation.PostConstruct;

/**
 * @author Zaw Than Oo
 * @since 14-04-2023
 */

@SpringBootApplication
@EnableAsync
@ComponentScan({"com.mutu.spring.*"})
@MapperScan(basePackages = { "com.mutu.spring.zgen.mapper", "com.mutu.spring.custommapper." })
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
    }
}
