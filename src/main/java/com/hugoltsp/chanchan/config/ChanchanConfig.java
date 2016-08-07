package com.hugoltsp.chanchan.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.hugoltsp.chanchan.exception.ChanchanConfigException;

@Component
public final class ChanchanConfig {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanConfig.class);

	private final List<String> catalogSeeds = new ArrayList<>();
	private final int numberOfCrawlers;
	private final int threadPoolSize;
	private final int requestDelay;
	private final String catalogSeedsPath;
	private final String outputPath;

	@Inject
	public ChanchanConfig(Environment env) throws ChanchanConfigException {
		try {

			int availableProcessors = Runtime.getRuntime().availableProcessors();

			this.catalogSeedsPath = env.getProperty("chanchan.catalogseeds");
			this.catalogSeeds.addAll(Collections
					.unmodifiableList(Files.lines(Paths.get(this.catalogSeedsPath)).collect(Collectors.toList())));
			this.numberOfCrawlers = env.getProperty("chanchan.numberofcrawlers", int.class, availableProcessors * 2);
			this.threadPoolSize = env.getProperty("chanchan.threadpoolsize", int.class, availableProcessors * 2);
			this.outputPath = env.getProperty("chanchan.output.path");
			this.requestDelay = env.getProperty("chanchan.requestdelay", int.class, 300);

			this.validateConfig();
		} catch (IOException e) {
			logger.error("Could not load catalogs file::", e);
			throw new ChanchanConfigException(e);
		} catch (ChanchanConfigException e) {
			logger.error("Config Error::", e);
			throw e;
		}
	}

	private void validateConfig() throws ChanchanConfigException {
		if (this.catalogSeeds.isEmpty()) {
			throw new ChanchanConfigException("At least one catalog must be specified");
		}

		if (this.numberOfCrawlers < 1) {
			throw new ChanchanConfigException("Invalid number of crawlers of:: " + this.numberOfCrawlers);
		}

		if (this.threadPoolSize < 1) {
			throw new ChanchanConfigException("Invalid thread pool size of::" + this.threadPoolSize);
		}

		if (this.requestDelay < 1) {
			throw new ChanchanConfigException("Invalid request delay of::" + this.requestDelay);
		}

		if (this.outputPath == null || "".equals(this.outputPath)) {
			throw new ChanchanConfigException("A ouyput path must be specified");
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

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public int getRequestDelay() {
		return requestDelay;
	}

}
