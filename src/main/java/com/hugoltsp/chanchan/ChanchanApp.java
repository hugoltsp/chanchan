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

	@Inject
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(ChanchanApp.class);
	}

	public void run(String... args) throws Exception {

		try {

			int numberOfCrawlers = Integer.parseInt(
					this.environment.getProperty("chanchan.numberofcrawlers", DEFAULT_NUMBER_OF_CRAWLERS + ""));
			String outputPath = this.environment.getProperty("chanchan.output.path");
			int requestDelay = Integer.parseInt(this.environment.getProperty("chanchan.requestdelay"));
			String[] seeds = this.environment.getProperty("chanchan.catalogseeds", String[].class);

			logger.info("Number of Concurrent Crawlers:: {}", numberOfCrawlers);
			logger.info("Output Directory:: {}", outputPath);
			logger.info("Seeds:: {}", Arrays.asList(seeds));
			logger.info("Request Delay Between Requests:: {}", requestDelay);

			Collection<String> threadUrls = crawlCatalogs(requestDelay, numberOfCrawlers, outputPath, seeds);

			crawlThreads(requestDelay, numberOfCrawlers, outputPath, threadUrls);

		} catch (Exception e) {
			logger.error("Error", e);
		}

	}

	private void crawlThreads(int requestDelay, int numberOfCrawlers, String ouputPath, Collection<String> threadUrls)
			throws Exception {
		logger.info("Crawling through threads");

		CrawlConfig config = new CrawlConfig();
		config.setPolitenessDelay(requestDelay);
		config.setCrawlStorageFolder(ouputPath);
		config.setIncludeBinaryContentInCrawling(true);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		for (String threadUrl : threadUrls) {
			controller.addSeed(threadUrl);
		}

		controller.startNonBlocking(new ChanchanWebCrawlerFactory(this.threadCrawler), numberOfCrawlers);
	}

	private Collection<String> crawlCatalogs(int requestDelay, int numberOfCrawlers, String ouputPath, String[] seeds)
			throws Exception {
		logger.info("Crawling through  catalogs");

		CrawlConfig config = new CrawlConfig();
		config.setPolitenessDelay(requestDelay);
		config.setCrawlStorageFolder(ouputPath);
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

		logger.info(threadUrls.size() + " Eligible thread urls have been found for these catalogs");

		return threadUrls;
	}

}
