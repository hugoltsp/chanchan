package com.teles.chanchan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.FourchanCatalogPage;
import com.teles.chanchan.domain.FourchanPost;
import com.teles.chanchan.domain.FourchanThread;
import com.teles.chanchan.domain.exception.ChanchanClientException;
import com.teles.chanchan.fourchan.client.FourchanChanFeignClient;

@Service
public class CrawlerService {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	private final FourchanChanFeignClient chanFeignClient;

	public CrawlerService(FourchanChanFeignClient fourchanChanFeignClient) {
		this.chanFeignClient = fourchanChanFeignClient;
	}

	public List<FourchanThread> crawl(List<String> boards) {
		logger.info("Crawling through catalogs:: {}", boards);
		List<FourchanCatalogPage> pages = this.pages(boards);
		List<FourchanThread> threads = this.threads(pages);
		return threads;
	}

	private List<FourchanCatalogPage> pages(List<String> catalogBoards) {
		return catalogBoards.stream().flatMap(this::mapToPages).collect(Collectors.toList());
	}

	private List<FourchanThread> threads(List<FourchanCatalogPage> pages) {
		return pages.parallelStream().flatMap(this::mapToThreads).peek(this::peek).map(this::mapThread)
				.collect(Collectors.toList());
	}

	private void peek(FourchanThread t) {
		logger.info("searching for posts on thread {} of {}", t.getNumber(), t.getBoard());
	}

	private Stream<FourchanCatalogPage> mapToPages(String board) {
		List<FourchanCatalogPage> catalogPages = new ArrayList<>();

		try {
			catalogPages = this.chanFeignClient.getCatalogPages(board);
		} catch (ChanchanClientException e) {
			logger.error("Couldn't find board {}", board);
		}

		return catalogPages.stream();
	}

	private Stream<FourchanThread> mapToThreads(FourchanCatalogPage c) {
		return c.getThreads().stream();
	}

	private FourchanThread mapThread(FourchanThread t) {

		try {
			List<FourchanPost> posts = this.chanFeignClient.getPosts(t);
			t.setPosts(posts);
		} catch (ChanchanClientException e) {
			logger.error("Couldn't find posts on thread {}", t.getNumber());
		}

		return t;
	}

}