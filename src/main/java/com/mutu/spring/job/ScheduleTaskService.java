package com.mutu.spring.job;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.mutu.spring.common.dto.LogMessage;
import com.mutu.spring.dto.CreateBatchJobDto;

@Service
public class ScheduleTaskService {
	
	Logger logger = LogManager.getLogger(ScheduleTaskService.class);
	
	@Autowired
	private TaskScheduler taskScheduler;

	Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

	public void addJob(CreateBatchJobDto createBatchJobDto) {
		logger.debug(new ObjectMessage(new LogMessage("Create new bash job is start creating..", createBatchJobDto)));
		
		CustomJob customJob = new CustomJob(createBatchJobDto.getJobName(), createBatchJobDto.getCronExp(), createBatchJobDto.getCurl());
		ScheduledFuture<?> scheduledTask = taskScheduler.schedule(customJob, new CronTrigger(customJob.getCronExp(), TimeZone.getTimeZone(TimeZone.getDefault().getID())));
		jobsMap.put(customJob.getJobName(), scheduledTask);
	}

	public void removeJob(String jobName) {
		ScheduledFuture<?> scheduledTask = jobsMap.get(jobName);
		if (scheduledTask != null) {
			scheduledTask.cancel(true);
			jobsMap.put(jobName, null);
		}
	}

	@EventListener({ ContextRefreshedEvent.class })
	void contextRefreshedEvent() {
		// Get all tasks from DB and reschedule them in case of context restarted
	}
}
