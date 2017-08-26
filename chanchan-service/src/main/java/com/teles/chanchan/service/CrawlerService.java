package com.teles.chanchan.service;

import java.util.ArrayList;
import java.util.List;
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

	public List<FourchanThread> crawlBoards(List<String> boards) {
		logger.info("Crawling through catalogs:: {}", boards);
		List<FourchanCatalogPage> pages = getCatalogs(boards);
		List<FourchanThread> threads = getThreads(pages);
		return threads;
	}

	private List<FourchanCatalogPage> getCatalogs(List<String> boards) {
		List<FourchanCatalogPage> catalogs = new ArrayList<>();

		for (String board : boards) {
			catalogs.addAll(getCatalogFromBoard(board));
		}

		return catalogs;
	}

	private List<FourchanCatalogPage> getCatalogFromBoard(String board) {
		List<FourchanCatalogPage> catalogPages = null;

		try {
			catalogPages = this.chanFeignClient.getCatalogPages(board);
		} catch (ChanchanClientException e) {
			logger.error("Couldn't find board {}", board);
		}

		return catalogPages;
	}

	private List<FourchanThread> getThreads(List<FourchanCatalogPage> pages) {
		List<FourchanThread> threads = new ArrayList<>();

		pages.parallelStream().flatMap(this::flatMapToThreads).peek(this::logThreads).forEach(t -> {
			t.setPosts(getPosts(t));
			threads.add(t);
		});

		return threads;
	}

	private void logThreads(FourchanThread t) {
		logger.info("searching for posts on thread {} of {}", t.getNumber(), t.getBoard());
	}

	private Stream<FourchanThread> flatMapToThreads(FourchanCatalogPage c) {
		return c.getThreads().stream();
	}

	private List<FourchanPost> getPosts(FourchanThread t) {
		List<FourchanPost> posts = null;

		try {
			posts = this.chanFeignClient.getPosts(t);
		} catch (ChanchanClientException e) {
			logger.error("Couldn't find posts on thread {}", t.getNumber());
		}

		return posts;
	}

}