package com.hugoltsp.chanchan;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	public static void main(String... args) {
		SpringApplication.run(ChanchanApp.class, args);
	}

	public void run(String... args) throws Exception {
		Arrays.asList(args).forEach(logger::info);

		String crawlStorageFolder = args[0];

		int numberOfCrawlers = DEFAULT_NUMBER_OF_CRAWLERS;

		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(crawlStorageFolder);

		config.setPolitenessDelay(100);

		config.setIncludeBinaryContentInCrawling(true);
		config.setMaxDepthOfCrawling(-1);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

// 	controller.addSeed("http://boards.4chan.org/wg/catalog");
		controller.addSeed("http://boards.4chan.org/wg/thread/4667225/new-to-wg-lets-get-you-started-1-look-before-you/");
		controller.start(ThreadCrawler.class, numberOfCrawlers);
	}

}
