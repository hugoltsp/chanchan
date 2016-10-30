package com.teles.chanchan.scraper.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.fourchan.FourchanCatalogPage;
import com.teles.chanchan.domain.fourchan.FourchanPost;
import com.teles.chanchan.fourchan.client.FourchanChanFeignClient;
import com.teles.chanchan.scraper.config.ChanchanConfig;

@Service
public class CrawlerService {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	private final MediaService mediaService;
	private final ThreadPoolTaskExecutor executor;
	private final FourchanChanFeignClient chanFeignClient;

	public CrawlerService(ChanchanConfig cfg, ThreadPoolTaskExecutor executor, MediaService mediaService) {
		this.mediaService = mediaService;
		this.executor = executor;
		this.chanFeignClient = new FourchanChanFeignClient(cfg.getChanApiUrl(), cfg.getChanCdnUrl());
	}

	public void crawl(List<String> catalogs) {
		Instant now = Instant.now();

		try {

			List<String> hrefs = this.crawlBoards(catalogs);

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
					java.lang.Thread.sleep(1000);
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

	private List<String> crawlBoards(List<String> catalogBoards) {
		logger.info("Crawling through catalogs");

		List<String> urls = new ArrayList<>();

		catalogBoards.parallelStream().forEach(board -> {
			List<FourchanCatalogPage> pages = this.chanFeignClient.getCatalogPages(board);

			logger.info("{} pages found for the following board:: {}", pages.size(), board);

			urls.addAll(pages.stream().flatMap(c -> c.getThreads().stream()).flatMap(t -> {
				logger.info("searching for posts on thread {} of {}", t.getNumber(), t.getBoard());
				return this.chanFeignClient.getPosts(t).stream();
			}).filter(p -> p.getFileExtension() != null).map(FourchanPost::getContentUrl).collect(Collectors.toList()));

		});

		logger.info(urls.size() + " Eligible posts have been found for these catalogs");

		return urls;
	}
}
