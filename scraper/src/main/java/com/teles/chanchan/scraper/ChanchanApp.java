package com.teles.chanchan.scraper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.teles.chanchan.scraper.config.ChanchanConfig;
import com.teles.chanchan.scraper.exception.ChanchanException;
import com.teles.chanchan.scraper.service.CrawlerService;

@SpringBootApplication
public class ChanchanApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanApp.class);

	private final ChanchanConfig config;
	private final CrawlerService crawlerService;

	public static void main(String[] args) {
		SpringApplication.run(ChanchanApp.class, args);
	}

	public ChanchanApp(ChanchanConfig cfg, CrawlerService crawlerService) {
		this.config = cfg;
		this.crawlerService = crawlerService;
	}

	public void run(String... args) throws Exception {

		try {

			if (args == null || args.length < 1) {
				throw new ChanchanException("At least one catalog must be specified");
			}

			List<String> seeds = Stream.of(args[0].split(",")).map(String::trim).map(String::toLowerCase)
					.collect(Collectors.toList());

			logger.info("Board Seeds:: {}", seeds);
			logger.info("Output Directory:: {}", this.config.getOutputPath());
			logger.info("Chanchan started");
			
			this.crawlerService.crawl(seeds);
			
		} catch (Exception e) {
			logger.error("Error", e);
		}

	}

}