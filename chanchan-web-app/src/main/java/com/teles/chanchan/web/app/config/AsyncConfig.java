package com.teles.chanchan.web.app.config;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.teles.chanchan.web.app.settings.AsyncSettings;

@EnableScheduling
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

	private final AsyncSettings settings;

	public AsyncConfig(AsyncSettings settings) {
		this.settings = settings;
	}

	@Bean
	public Executor getAsyncExecutor() {
		logger.info("Configuring Async Executor");

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(settings.getThreadPoolSize());
		executor.setThreadNamePrefix(settings.getThreadNamePrefix());
		executor.setDaemon(true);

		return executor;
	}

	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}
