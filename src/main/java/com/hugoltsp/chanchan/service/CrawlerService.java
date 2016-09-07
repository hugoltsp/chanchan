package com.hugoltsp.chanchan.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.hugoltsp.chanchan.config.ChanchanConfig;
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

	private final MediaService mediaService;
	private final ThreadPoolTaskExecutor executor;
	private final String outputPath;
	private final int numberOfCrawlers;
	private final int requestDelay;

	public CrawlerService(ChanchanConfig cfg, ThreadPoolTaskExecutor executor, MediaService mediaService) {
		this.numberOfCrawlers = cfg.getNumberOfCrawlers();
		this.outputPath = cfg.getOutputPath();
		this.requestDelay = cfg.getRequestDelay();
		this.mediaService = mediaService;
		this.executor = executor;
	}

	public void crawl(List<String> catalogs) {
		Instant now = Instant.now();

		try {

			List<String> threadsToCrawl = this.crawlCatalogs(catalogs);
			List<String> hrefs = this.crawlThreads(threadsToCrawl);

			for (String href : hrefs) {
				this.mediaService.download(href);
			}

		} catch (Exception e) {
			logger.error("Error: ", e);
		} finally {
			this.executor.shutdown();

			ThreadPoolExecutor threadPoolExecutor = executor.getThreadPoolExecutor();
			while (threadPoolExecutor.isTerminating()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				if (threadPoolExecutor.isTerminated()) {
					Duration duration = Duration.between(now, Instant.now());
					logger.info("Chanchan finished in {} seconds", duration.getSeconds());
					break;
				}
			}

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
