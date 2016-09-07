package com.hugoltsp.chanchan;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.hugoltsp.chanchan.config.ChanchanConfig;
import com.hugoltsp.chanchan.service.CrawlerService;

@SpringBootApplication
@ComponentScan(basePackages = "com.hugoltsp.chanchan")
public class ChanchanApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanApp.class);

	private final ChanchanConfig config;
	private final CrawlerService crawlerService;

	public static void main(String[] args) {
		SpringApplication.run(ChanchanApp.class);
	}

	public ChanchanApp(ChanchanConfig cfg, CrawlerService crawlerService) {
		this.config = cfg;
		this.crawlerService = crawlerService;
	}

	public void run(String... args) throws Exception {

		try {

			logger.info("Reading Catalog File At:: {}", this.config.getCatalogSeedsPath());

			List<String> seeds = this.config.getCatalogSeeds();

			logger.info("Number of Concurrent Crawlers:: {}", this.config.getNumberOfCrawlers());
			logger.info("Output Directory:: {}", this.config.getOutputPath());
			logger.info("Catalog Seeds:: {}", seeds);
			logger.info("Delay Between Requests:: {}", this.config.getRequestDelay());

			logger.info("Chanchan started");
			this.crawlerService.crawl(seeds);
		} catch (Exception e) {
			logger.error("Error", e);
		}

	}

}