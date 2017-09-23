package com.teles.chanchan.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	private static final Logger logger = LoggerFactory.getLogger(Scheduled.class);
	
	@Scheduled(fixedDelay = 1000)
	public void schedule() {
		logger.info("test");
	}

}