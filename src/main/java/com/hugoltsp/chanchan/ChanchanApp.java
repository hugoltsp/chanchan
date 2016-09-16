package com.hugoltsp.chanchan;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.hugoltsp.chanchan.config.ChanchanConfig;
import com.hugoltsp.chanchan.exception.ChanchanException;
import com.hugoltsp.chanchan.service.CrawlerService;

@SpringBootApplication
@ComponentScan(basePackages = "com.hugoltsp.chanchan")
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