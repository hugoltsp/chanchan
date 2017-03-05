package com.teles.chanchan.scraper.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.fourchan.FourchanCatalogPage;
import com.teles.chanchan.domain.fourchan.FourchanPost;
import com.teles.chanchan.domain.fourchan.FourchanThread;
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
		Instant start = Instant.now();

		try {
			
			List<FourchanThread> threads = this.getThreads(catalogs);
		
 			this.downloadImagesFromThreads(threads);
			
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
					Duration duration = Duration.between(start, Instant.now());
					logger.info("Chanchan finished in {} seconds", duration.getSeconds());
					break;
				}
			}

		}
	}

	private List<FourchanThread> getThreads(List<String> catalogBoards) {
		logger.info("Crawling through catalogs");
		
		List<FourchanThread> threads = new ArrayList<>();
		
		for (String catalog : catalogBoards) {
			
			List<FourchanCatalogPage> pages = this.chanFeignClient.getCatalogPages(catalog);

			logger.info("{} pages found for the following board:: {}", pages.size(), catalog);

			for (FourchanCatalogPage page : pages) {
			
				for (FourchanThread thread : page.getThreads()) {
					logger.info("searching for posts on thread {} of {}", thread.getNumber(), thread.getBoard());
					
					List<FourchanPost> posts = this.chanFeignClient.getPosts(thread);
					
					thread.addPosts(posts);
					threads.add(thread);
					
				}
				
			}
			
		}

		return threads;
	}
	
	private void downloadImagesFromThreads(List<FourchanThread> threads){
		threads.stream().flatMap(t -> t.getPosts().stream())
				.filter(p -> p.getFileExtension() != null).map(FourchanPost::getContentUrl)
				.forEach(this.mediaService::download);
	}
	
}