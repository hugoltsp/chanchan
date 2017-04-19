package com.teles.chanchan.scraper.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.data.repository.FourchanThreadRepository;
import com.teles.chanchan.domain.fourchan.FourchanCatalogPage;
import com.teles.chanchan.domain.fourchan.FourchanPost;
import com.teles.chanchan.domain.fourchan.FourchanThread;
import com.teles.chanchan.fourchan.client.FourchanChanFeignClient;
import com.teles.chanchan.scraper.config.ChanchanConfig;

@Service
public class CrawlerService {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	private final FourchanChanFeignClient chanFeignClient;
	private final FourchanThreadRepository fourchanThreadRepository;

	public CrawlerService(ChanchanConfig cfg,
			FourchanThreadRepository fourchanThreadRepository) {
		this.fourchanThreadRepository = fourchanThreadRepository;
		this.chanFeignClient = new FourchanChanFeignClient(cfg.getChanApiUrl(), cfg.getChanCdnUrl());
	}

	public void crawl(List<String> catalogs) {
		List<FourchanThread> threads = this.getThreads(catalogs);
		this.fourchanThreadRepository.save(threads);
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

}