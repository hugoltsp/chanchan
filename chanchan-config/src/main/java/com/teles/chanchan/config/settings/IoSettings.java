package com.teles.chanchan.config.settings;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("chanchan.io")
public class IoSettings {

	private static final Logger logger = LoggerFactory.getLogger(IoSettings.class);

	@PostConstruct
	private void init() {
		logger.info(toString());
	}

	private String outputPath;

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	@Override
	public String toString() {
		return "IoSettings [outputPath=" + outputPath + "]";
	}

}
