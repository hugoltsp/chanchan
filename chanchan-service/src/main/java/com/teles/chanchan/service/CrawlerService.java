package com.teles.chanchan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.exception.ChanchanClientException;
import com.teles.chanchan.domain.response.CatalogPageResponse;
import com.teles.chanchan.domain.response.PostResponse;
import com.teles.chanchan.domain.response.ThreadResponse;
import com.teles.chanchan.fourchan.api.client.FourchanChanFeignClient;

@Service
public class CrawlerService {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	private final FourchanChanFeignClient chanFeignClient;

	public CrawlerService(FourchanChanFeignClient fourchanChanFeignClient) {
		this.chanFeignClient = fourchanChanFeignClient;
	}

	public List<ThreadResponse> crawlBoards(List<String> boards) {
		logger.info("Crawling through catalogs:: {}", boards);
		List<CatalogPageResponse> pages = getCatalogs(boards);
		List<ThreadResponse> threads = getThreads(pages);
		return threads;
	}

	private List<CatalogPageResponse> getCatalogs(List<String> boards) {
		List<CatalogPageResponse> catalogs = new ArrayList<>();

		for (String board : boards) {
			catalogs.addAll(getCatalogFromBoard(board));
		}

		return catalogs;
	}

	private List<CatalogPageResponse> getCatalogFromBoard(String board) {
		List<CatalogPageResponse> catalogPages = null;

		try {
			catalogPages = this.chanFeignClient.getCatalogPages(board);
		} catch (ChanchanClientException e) {
			logger.error("Couldn't find board {}", board);
		}

		return catalogPages;
	}

	private List<ThreadResponse> getThreads(List<CatalogPageResponse> pages) {
		List<ThreadResponse> threads = new ArrayList<>();

		pages.parallelStream().flatMap(this::flatMapToThreads).peek(this::logThreads).forEach(t -> {
			t.setPosts(getPosts(t));
			threads.add(t);
		});

		return threads;
	}

	private void logThreads(ThreadResponse t) {
		logger.info("searching for posts on thread {} of {}", t.getNumber(), t.getBoard());
	}

	private Stream<ThreadResponse> flatMapToThreads(CatalogPageResponse c) {
		return c.getThreads().stream();
	}

	private List<PostResponse> getPosts(ThreadResponse t) {
		List<PostResponse> posts = null;

		try {
			posts = this.chanFeignClient.getPosts(t);
		} catch (ChanchanClientException e) {
			logger.error("Couldn't find posts on thread {}", t.getNumber());
		}

		return posts;
	}

}