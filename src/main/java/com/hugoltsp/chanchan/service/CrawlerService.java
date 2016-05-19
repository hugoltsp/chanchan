package com.hugoltsp.chanchan.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.hugoltsp.chanchan.crawlers.CatalogCrawler;
import com.hugoltsp.chanchan.crawlers.ThreadCrawler;
import com.hugoltsp.chanchan.crawlers.factory.ChanchanWebCrawlerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Service
public class CrawlerService {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	@Inject
	private ImageService imageService;

	@Inject
	private ThreadPoolTaskExecutor executor;

	private final String outputPath;
	private final int numberOfCrawlers;
	private final int requestDelay;

	@Inject
	public CrawlerService(Environment environment) {
		this.numberOfCrawlers = environment.getProperty("chanchan.numberofcrawlers", int.class,
				Runtime.getRuntime().availableProcessors());
		this.outputPath = environment.getProperty("chanchan.output.path");
		this.requestDelay = environment.getProperty("chanchan.requestdelay", int.class);
	}

	public void crawl(List<String> catalogs) {
		try {
			List<String> threadsToCrawl = this.crawlCatalogs(catalogs);
			List<String> hrefs = this.crawlThreads(threadsToCrawl);

			for (String href : hrefs) {
				this.imageService.download(href);
			}

		} catch (Exception e) {
			logger.error("Error: ", e);
		} finally {
			this.executor.shutdown();
		}
	}

	private List<String> crawlThreads(List<String> threadUrls) throws Exception {
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

		ChanchanWebCrawlerFactory crawlerFactory = new ChanchanWebCrawlerFactory(ThreadCrawler::new);

		controller.startNonBlocking(crawlerFactory, numberOfCrawlers);
		controller.waitUntilFinish();
		
		List<String> urls = crawlerFactory.getUrls();
		
		logger.info(urls.size() + " images found..");
		
		return urls;
	}

	private List<String> crawlCatalogs(List<String> catalogUrls) throws Exception {
		logger.info("Crawling through catalogs");

		CrawlConfig config = new CrawlConfig();
		config.setPolitenessDelay(requestDelay);
		config.setCrawlStorageFolder(outputPath);
		config.setMaxDepthOfCrawling(2);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		for (String seed : catalogUrls) {
			controller.addSeed(seed);
		}

		ChanchanWebCrawlerFactory crawlerFactory = new ChanchanWebCrawlerFactory(CatalogCrawler::new);
		controller.startNonBlocking(crawlerFactory, numberOfCrawlers);
		controller.waitUntilFinish();

		List<String> urls = crawlerFactory.getUrls();

		logger.info(urls.size() + " Eligible threads have been found for these catalogs");

		return urls;
	}
}
