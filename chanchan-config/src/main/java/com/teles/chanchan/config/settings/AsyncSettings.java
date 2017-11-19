package com.teles.chanchan.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("chanchan.async")
public class AsyncSettings {

	private int threadPoolSize;
	private String threadNamePrefix;

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

	public String toString() {
		return "Async [threadPoolSize=" + threadPoolSize + ", threadNamePrefix=" + threadNamePrefix + "]";
	}

}