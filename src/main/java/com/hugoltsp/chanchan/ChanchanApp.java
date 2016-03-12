package com.hugoltsp.chanchan;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.hugoltsp.chanchan.crawlers.CatalogCrawler;
import com.hugoltsp.chanchan.crawlers.ThreadCrawler;
import com.hugoltsp.chanchan.crawlers.factory.ChanchanWebCrawlerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@SpringBootApplication
@ComponentScan(basePackages = "com.hugoltsp.chanchan")
public class ChanchanApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanApp.class);

	private static final int DEFAULT_NUMBER_OF_CRAWLERS = Runtime.getRuntime().availableProcessors();

	@Inject
	private ThreadCrawler threadCrawler;

	@Inject
	private CatalogCrawler catalogCrawler;

	private final int numberOfCrawlers;
	private final String outputPath;
	private final int requestDelay;
	private final String[] seeds;

	@Inject
	public ChanchanApp(Environment environment) {
		numberOfCrawlers = Integer
				.parseInt(environment.getProperty("chanchan.numberofcrawlers", DEFAULT_NUMBER_OF_CRAWLERS + ""));
		outputPath = environment.getProperty("chanchan.output.path");
		requestDelay = Integer.parseInt(environment.getProperty("chanchan.requestdelay"));
		seeds = environment.getProperty("chanchan.catalogseeds", String[].class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ChanchanApp.class);
	}

	public void run(String... args) throws Exception {

		try {

			logger.info("Number of Concurrent Crawlers:: {}", numberOfCrawlers);
			logger.info("Output Directory:: {}", outputPath);
			logger.info("Seeds:: {}", Arrays.asList(seeds));
			logger.info("Request Delay Between Requests:: {}", requestDelay);

			Collection<String> catalogCrawlerController = crawlCatalogs();

		} catch (Exception e) {
			logger.error("Error", e);
		}

	}

	private Collection<String> crawlCatalogs() throws Exception {
		logger.info("Crawling through image catalogs");

		CrawlConfig config = new CrawlConfig();
		config.setPolitenessDelay(this.requestDelay);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		for (String seed : this.seeds) {
			controller.addSeed(seed);
		}

		controller.startNonBlocking(new ChanchanWebCrawlerFactory(this.catalogCrawler), this.numberOfCrawlers);
		controller.waitUntilFinish();
		
		return this.catalogCrawler.getThreadUrls();
	}

}
