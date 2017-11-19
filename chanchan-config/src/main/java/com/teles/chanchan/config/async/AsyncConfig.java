package com.teles.chanchan.config.async;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.teles.chanchan.config.settings.AsyncSettings;

@EnableAsync
@SpringBootConfiguration
public class AsyncConfig implements AsyncConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

	private final AsyncSettings settings;

	public AsyncConfig(AsyncSettings settings) {
		this.settings = settings;
	}

	@Bean
	public Executor getAsyncExecutor() {
		logger.info("Getting Async Executor");
		logger.info("Threadpool size:: {}", settings.getThreadPoolSize());

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(settings.getThreadPoolSize());
		executor.setThreadNamePrefix(settings.getThreadNamePrefix());

		return executor;
	}

	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}
