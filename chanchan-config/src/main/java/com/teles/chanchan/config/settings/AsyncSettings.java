package com.teles.chanchan.config.settings;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("chanchan.async")
public class AsyncSettings {

	private static final Logger logger = LoggerFactory.getLogger(AsyncSettings.class);

	private int threadPoolSize;
	private String threadNamePrefix;

	@PostConstruct
	private void init() {
		logger.info(toString());
	}

	public String getThreadNamePrefix() {
		return threadNamePrefix;
	}

	public void setThreadNamePrefix(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	@Override
	public String toString() {
		return "AsyncSettings [threadPoolSize=" + threadPoolSize + ", threadNamePrefix=" + threadNamePrefix + "]";
	}

}