package com.teles.chanchan.web.app.settings;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("chanchan.async")
public class AsyncSettings {

	private static final Logger logger = LoggerFactory.getLogger(AsyncSettings.class);

	private Integer threadPoolSize;
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

	public Integer getThreadPoolSize() {
		return threadPoolSize == null ? getDefaultThreadPoolSize() : threadPoolSize;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	private static Integer getDefaultThreadPoolSize() {
		return Runtime.getRuntime().availableProcessors() * 2;
	}

	@Override
	public String toString() {
		return "AsyncSettings [getThreadNamePrefix()=" + getThreadNamePrefix() + ", getThreadPoolSize()="
				+ getThreadPoolSize() + "]";
	}

}