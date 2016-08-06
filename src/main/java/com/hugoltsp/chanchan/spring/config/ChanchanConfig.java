package com.hugoltsp.chanchan.spring.config;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.hugoltsp.chanchan.exception.ChanchanException;

@Component
public final class ChanchanConfig {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanConfig.class);

	private final List<String> catalogSeeds;
	private final String catalogSeedsPath;
	private final int numberOfCrawlers;
	private final String outputPath;
	private final String requestDelay;

	public ChanchanConfig(Environment env) {
		try {

			int availableProcessors = Runtime.getRuntime().availableProcessors();

			this.catalogSeedsPath = env.getProperty("chanchan.catalogseeds");
			this.catalogSeeds = Collections
					.unmodifiableList(Files.lines(Paths.get(catalogSeedsPath)).collect(Collectors.toList()));
			this.numberOfCrawlers = env.getProperty("chanchan.numberofcrawlers", Integer.class, availableProcessors);
			this.outputPath = env.getProperty("chanchan.output.path");
			this.requestDelay = env.getProperty("chanchan.requestdelay");

		} catch (Exception e) {
			logger.error("Config Error::", e);
			throw new ChanchanException(e);
		}
	}

	public List<String> getCatalogSeeds() {
		return catalogSeeds;
	}

	public String getCatalogSeedsPath() {
		return catalogSeedsPath;
	}

	public int getNumberOfCrawlers() {
		return numberOfCrawlers;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public String getRequestDelay() {
		return requestDelay;
	}

}
