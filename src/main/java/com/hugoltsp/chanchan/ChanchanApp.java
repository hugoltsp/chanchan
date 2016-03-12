package com.hugoltsp.chanchan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.hugoltsp.chanchan.crawlers.ThreadCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@SpringBootApplication
public class ChanchanApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ChanchanApp.class);

	private static final int DEFAULT_NUMBER_OF_CRAWLERS = Runtime.getRuntime().availableProcessors();

	@Autowired
	private Environment environment;

	public static void main(String... args) {
		SpringApplication.run(ChanchanApp.class, args);
	}

	public void run(String... args) throws Exception {
		final int numberOfCrawlers = Integer
				.parseInt(this.environment.getProperty("chanchan.numberofcrawlers", DEFAULT_NUMBER_OF_CRAWLERS + ""));
		final String outputPath = this.environment.getProperty("chanchan.output.path");
		final String[] seeds = this.environment.getProperty("chanchan.boardseeds", String[].class);
		final int requestDelay = Integer.parseInt(this.environment.getProperty("chanchan.requestdelay"));

		CrawlConfig config = new CrawlConfig();
		config.setIncludeBinaryContentInCrawling(true);

		config.setCrawlStorageFolder(outputPath);
		config.setPolitenessDelay(requestDelay);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		for (String seed : seeds) {
			controller.addSeed(seed);
		}

		controller.start(ThreadCrawler.class, numberOfCrawlers);
	}

}
