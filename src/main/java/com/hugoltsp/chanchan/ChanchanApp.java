package com.hugoltsp.chanchan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
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

	@Inject
	private Environment environment;

	private int numberOfCrawlers;

	private String outputPath;

	private int requestDelay;

	public static void main(String[] args) {
		SpringApplication.run(ChanchanApp.class);
	}

	public void run(String... args) throws Exception {

		try {

			numberOfCrawlers = Integer.parseInt(
					this.environment.getProperty("chanchan.numberofcrawlers", DEFAULT_NUMBER_OF_CRAWLERS + ""));
			outputPath = this.environment.getProperty("chanchan.output.path");
			requestDelay = Integer.parseInt(this.environment.getProperty("chanchan.requestdelay"));

			logger.info("Reading Catalog File At:: {}", this.environment.getProperty("chanchan.catalogseeds"));
			
			List<String> seeds = getCatalogs(this.environment.getProperty("chanchan.catalogseeds"));

			logger.info("Number of Concurrent Crawlers:: {}", numberOfCrawlers);
			logger.info("Output Directory:: {}", outputPath);
			logger.info("Catalog Seeds:: {}", seeds);
			logger.info("Delay Between Requests:: {}", requestDelay);

			long start = System.currentTimeMillis();
			logger.info("Chanchan started");

			Collection<String> threadUrls = crawlCatalogs(seeds);
			crawlThreads(threadUrls);

			logger.info("Success! Chanchan finished in {} ", (System.currentTimeMillis() - start) / 1000);
		} catch (Exception e) {
			logger.error("Error", e);
		}

	}

	private void crawlThreads(Collection<String> threadUrls) throws Exception {
		logger.info("Crawling through threads");

		CrawlConfig config = new CrawlConfig();
		config.setPolitenessDelay(requestDelay);
		config.setCrawlStorageFolder(outputPath);
		config.setIncludeBinaryContentInCrawling(true);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		for (String threadUrl : threadUrls) {
			controller.addSeed(threadUrl);
		}

		controller.startNonBlocking(new ChanchanWebCrawlerFactory(this.threadCrawler), numberOfCrawlers);
		controller.waitUntilFinish();
	}

	private Collection<String> crawlCatalogs(List<String> seeds) throws Exception {
		logger.info("Crawling through  catalogs");

		CrawlConfig config = new CrawlConfig();
		config.setPolitenessDelay(requestDelay);
		config.setCrawlStorageFolder(outputPath);
		config.setMaxDepthOfCrawling(2);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		for (String seed : seeds) {
			controller.addSeed(seed);
		}

		controller.startNonBlocking(new ChanchanWebCrawlerFactory(this.catalogCrawler), numberOfCrawlers);
		controller.waitUntilFinish();

		Collection<String> threadUrls = this.catalogCrawler.getThreadUrls();

		logger.info(threadUrls.size() + " Eligible threads have been found for these catalogs");

		return threadUrls;
	}

	private static List<String> getCatalogs(String catalogsFilePath) throws IOException {
		List<String> catalogs = Files.lines(Paths.get(catalogsFilePath)).collect(Collectors.toList());
		return catalogs;
	}

}