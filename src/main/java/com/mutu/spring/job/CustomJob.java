package com.mutu.spring.job;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import com.mutu.spring.common.dto.LogMessage;


public class CustomJob implements Runnable {
	
	Logger logger = LogManager.getLogger(CustomJob.class);
	
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
//		Logger logger = LogManager.getLogger("elasticsearch");
//
//        ThreadContext.put("myFavouriteVariable", UUID.randomUUID().toString());
//        ThreadContext.put("anotherFavVariable", "");
//        ThreadContext.put("myStructuredJSON", "{\"yes\":100,\"you\":\"can\"}");
//        logger.info("Hello, World!");
//        ThreadContext.remove("myFavouriteVariable");
//        ThreadContext.remove("myStructuredJSON");
//
//        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
//
//        LoggingSystem.get(logger.getClass().getClassLoader()).getShutdownHandler().run();		
//		System.out.println("I am running...");
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream("curl");
			String curl = IOUtils.toString(is, StandardCharsets.UTF_8);
			curl = curl.replace("\n", "").replace("\r", "");
			curl = curl.replace("\\--", "--");
			curl = curl.replaceAll("\\s{2,}", " ").trim();
			System.out.println(curl);		
			
			ProcessBuilder pb = new ProcessBuilder(curl);
			pb.redirectErrorStream(true);
			Process pr = pb.start();
			
//			Runtime rt = Runtime.getRuntime();
//			Process pr = rt.exec(curl);

			InputStream responseIs = pr.getInputStream();
			String response = IOUtils.toString(responseIs, StandardCharsets.UTF_8);
			System.out.println(response);
			logger.debug(new ObjectMessage(new LogMessage("CURL call response " + response, null)));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new ObjectMessage(new LogMessage("CURL call faield", e)));
		}
	}
}
