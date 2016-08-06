package com.hugoltsp.chanchan.spring.config;

import java.io.IOException;
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

	public ChanchanConfig(Environment env) {
		try {

			this.catalogSeedsPath = env.getProperty("chanchan.catalogseeds");
			this.catalogSeeds = Collections
					.unmodifiableList(Files.lines(Paths.get(catalogSeedsPath)).collect(Collectors.toList()));
			
		} catch (Exception e) {
			logger.error("Config Error::", e);
			throw new ChanchanException(e);
		}
	}

}
