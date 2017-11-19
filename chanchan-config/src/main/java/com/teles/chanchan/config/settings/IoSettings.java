package com.teles.chanchan.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("chanchan.io")
public class IoSettings {

	private String outputPath;

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String toString() {
		return "Io [outputPath=" + outputPath + "]";
	}

}
