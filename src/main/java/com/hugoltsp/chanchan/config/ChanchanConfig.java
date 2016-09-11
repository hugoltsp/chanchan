package com.hugoltsp.chanchan.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.hugoltsp.chanchan.exception.ChanchanConfigException;

@Component
public final class ChanchanConfig {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanConfig.class);

	private final List<String> catalogBoards = new ArrayList<>();
	private final int numberOfCrawlers;
	private final int threadPoolSize;
	private final int requestDelay;
	private final String catalogSeedsPath;
	private final String outputPath;
	private final String chanApiUrl;
	private final String chanCdnUrl;

	public ChanchanConfig(Environment env) throws ChanchanConfigException {
		try {
			int availableProcessors = Runtime.getRuntime().availableProcessors();

			this.catalogSeedsPath = env.getProperty("chanchan.catalogseeds");
			this.catalogBoards.addAll(Collections
					.unmodifiableList(Files.lines(Paths.get(this.catalogSeedsPath)).collect(Collectors.toList())));
			this.numberOfCrawlers = env.getProperty("chanchan.numberofcrawlers", int.class, availableProcessors * 2);
			this.threadPoolSize = env.getProperty("chanchan.threadpoolsize", int.class, availableProcessors * 2);
			this.outputPath = env.getProperty("chanchan.output.path");
			this.requestDelay = env.getProperty("chanchan.requestdelay", int.class, 300);
			this.chanApiUrl = env.getProperty("4chan.api.url", "http://api.4chan.org");
			this.chanCdnUrl = env.getProperty("4chan.cdn.url", "http://i.4cdn.org");

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
		if (this.catalogBoards.isEmpty()) {
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
			throw new ChanchanConfigException("An output path must be specified");
		}

		if (this.chanApiUrl == null || "".equals(this.chanApiUrl)) {
			throw new ChanchanConfigException("4chan api url absent");
		}
	}

	public List<String> getCatalogBoards() {
		return catalogBoards;
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

	public String getChanApiUrl() {
		return chanApiUrl;
	}

	public String getChanCdnUrl() {
		return chanCdnUrl;
	}

	@Override
	public String toString() {
		return "ChanchanConfig [catalogBoards=" + catalogBoards + ", numberOfCrawlers=" + numberOfCrawlers
				+ ", threadPoolSize=" + threadPoolSize + ", requestDelay=" + requestDelay + ", catalogSeedsPath="
				+ catalogSeedsPath + ", outputPath=" + outputPath + ", chanApiUrl=" + chanApiUrl + ", chanCdnUrl="
				+ chanCdnUrl + "]";
	}

}
