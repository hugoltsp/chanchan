package com.teles.chanchan.config.cache;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import com.google.common.cache.CacheBuilder;
import com.teles.chanchan.domain.constants.GuavaCacheConstants;

@EnableCaching
@SpringBootConfiguration
public class CacheConfig {

	private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

	@Bean
	public CacheManager cacheManager() {
		logger.info("Guava Cache starting...");
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		simpleCacheManager.setCaches(Arrays.asList(buildCache()));
		return simpleCacheManager;
	}

	private static GuavaCache buildCache() {
		return new GuavaCache(GuavaCacheConstants.DEFAULT_CACHE, CacheBuilder.newBuilder().build());
	}

}