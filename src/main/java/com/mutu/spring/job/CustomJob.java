package com.mutu.spring.job;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.logging.LoggingSystem;

public class CustomJob implements Runnable {
	private String jobName;
	private String cronExp;
	private String curl;
	
	public CustomJob(String jobName, String cronExp, String curl) {
		this.jobName = jobName;
		this.cronExp = cronExp;
		this.curl = curl;
	}

	public String getJobName() {
		return jobName;
	}

	public String getCronExp() {
		return cronExp;
	}

	public String getCurl() {
		return curl;
	}

	@Override
	public void run() {
		Logger logger = LogManager.getLogger("elasticsearch");

        ThreadContext.put("myFavouriteVariable", UUID.randomUUID().toString());
        ThreadContext.put("anotherFavVariable", "");
        ThreadContext.put("myStructuredJSON", "{\"yes\":100,\"you\":\"can\"}");
        logger.info("Hello, World!");
        ThreadContext.remove("myFavouriteVariable");
        ThreadContext.remove("myStructuredJSON");

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));

        LoggingSystem.get(logger.getClass().getClassLoader()).getShutdownHandler().run();		
		System.out.println("I am running...");
	}
}
