package com.hugoltsp.chanchan.config.async;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(AsyncConfiguration.class);

	@Inject
	private Environment environment;

	@Override
	@Bean
	public Executor getAsyncExecutor() {
		logger.info("Getting Async Executor");
		logger.info("Threadpool size:: {}", this.environment.getProperty("chanchan.threadpoolsize", int.class,
				(2 * Runtime.getRuntime().availableProcessors())));
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(this.environment.getProperty("chanchan.threadpoolsize", int.class,
				(2 * Runtime.getRuntime().availableProcessors())));
		
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
