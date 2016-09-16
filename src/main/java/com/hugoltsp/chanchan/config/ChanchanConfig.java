package com.hugoltsp.chanchan.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.hugoltsp.chanchan.exception.ChanchanConfigException;

@Component
public final class ChanchanConfig {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanConfig.class);

	private final int threadPoolSize;
	private final int requestDelay;
	private final String outputPath;
	private final String chanApiUrl;
	private final String chanCdnUrl;

	public ChanchanConfig(Environment env) throws ChanchanConfigException {
		try {
			int availableProcessors = Runtime.getRuntime().availableProcessors();

			this.threadPoolSize = env.getProperty("chanchan.threadpoolsize", int.class, availableProcessors * 2);
			this.outputPath = env.getProperty("chanchan.output.path");
			this.requestDelay = env.getProperty("chanchan.requestdelay", int.class, 300);
			this.chanApiUrl = env.getProperty("4chan.api.url", "http://api.4chan.org");
			this.chanCdnUrl = env.getProperty("4chan.cdn.url", "http://i.4cdn.org");

			this.validateConfig();
		} catch (ChanchanConfigException e) {
			logger.error("Config Error::", e);
			throw e;
		}
	}

	private void validateConfig() throws ChanchanConfigException {

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

}