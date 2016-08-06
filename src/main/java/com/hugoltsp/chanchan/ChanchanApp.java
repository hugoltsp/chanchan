package com.hugoltsp.chanchan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.hugoltsp.chanchan.service.CrawlerService;
import com.hugoltsp.chanchan.spring.config.ChanchanConfig;

@SpringBootApplication
@ComponentScan(basePackages = "com.hugoltsp.chanchan")
public class ChanchanApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanApp.class);

	private static final int DEFAULT_NUMBER_OF_CRAWLERS = Runtime.getRuntime().availableProcessors();

	@Inject
	private Environment environment;

	@Inject
	private CrawlerService crawlerService;

	public ChanchanApp(ChanchanConfig cfg) {
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ChanchanApp.class);
	}

	public void run(String... args) throws Exception {

		try {

			logger.info("Reading Catalog File At:: {}", this.environment.getProperty("chanchan.catalogseeds"));

			List<String> seeds = getCatalogs(this.environment.getProperty("chanchan.catalogseeds"));

			logger.info("Number of Concurrent Crawlers:: {}", this.environment.getProperty("chanchan.numberofcrawlers", DEFAULT_NUMBER_OF_CRAWLERS + ""));
			logger.info("Output Directory:: {}", this.environment.getProperty("chanchan.output.path"));
			logger.info("Catalog Seeds:: {}", seeds);
			logger.info("Delay Between Requests:: {}", this.environment.getProperty("chanchan.requestdelay"));

			logger.info("Chanchan started");
			this.crawlerService.crawl(seeds);
		} catch (Exception e) {
			logger.error("Error", e);
		}

	}

	private static List<String> getCatalogs(String catalogsFilePath) throws IOException {
		List<String> catalogs = Files.lines(Paths.get(catalogsFilePath)).collect(Collectors.toList());
		return catalogs;
	}

}