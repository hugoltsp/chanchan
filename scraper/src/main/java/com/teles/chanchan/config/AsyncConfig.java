package com.teles.chanchan.config;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);
	
	private final int threadPoolSize;

	public AsyncConfig(ChanchanConfig cfg) {
		this.threadPoolSize = cfg.getThreadPoolSize();
	}
	
	@Override
	@Bean
	public Executor getAsyncExecutor() {
		logger.info("Getting Async Executor");
		logger.info("Threadpool size:: {}", this.threadPoolSize);
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(this.threadPoolSize);
		executor.setThreadNamePrefix("ChanChan-Executor-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);

		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}
