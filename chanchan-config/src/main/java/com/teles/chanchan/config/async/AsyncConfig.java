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

import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.domain.settings.ChanchanSettings.Async;

@EnableAsync
@SpringBootConfiguration
public class AsyncConfig implements AsyncConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

	private final ChanchanSettings settings;

	public AsyncConfig(ChanchanSettings settings) {
		this.settings = settings;
	}

	@Bean
	public Executor getAsyncExecutor() {
		logger.info("Getting Async Executor");
		Async async = this.settings.getAsync();
		logger.info("Threadpool size:: {}", async.getThreadPoolSize());

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(async.getThreadPoolSize());
		executor.setThreadNamePrefix(async.getThreadNamePrefix());

		return executor;
	}

	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}
