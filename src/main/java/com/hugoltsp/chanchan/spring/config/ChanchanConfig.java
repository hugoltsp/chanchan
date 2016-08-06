package com.hugoltsp.chanchan.spring.config;

import java.nio.file.Files;
import java.nio.file.Paths;
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

	private final List<String> catalogSeeds;
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
			this.catalogSeeds = Collections
					.unmodifiableList(Files.lines(Paths.get(this.catalogSeedsPath)).collect(Collectors.toList()));
			this.numberOfCrawlers = env.getProperty("chanchan.numberofcrawlers", int.class, availableProcessors * 2);
			this.threadPoolSize = env.getProperty("chanchan.threadpoolsize", int.class, availableProcessors * 2);
			this.outputPath = env.getProperty("chanchan.output.path");
			this.requestDelay = env.getProperty("chanchan.requestdelay", int.class, 300);

		} catch (Exception e) {
			logger.error("Config Error::", e);
			throw new ChanchanConfigException(e);
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
