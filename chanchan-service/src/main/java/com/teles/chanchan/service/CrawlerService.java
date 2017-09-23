package com.teles.chanchan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.response.CatalogResponse;
import com.teles.chanchan.domain.response.PostResponse;
import com.teles.chanchan.domain.response.ThreadResponse;
import com.teles.chanchan.fourchan.api.client.FourchanChanResourceClient;
import com.teles.chanchan.fourchan.api.client.exception.ChanchanClientException;

@Service
public class CrawlerService {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	private final FourchanChanResourceClient chanFeignClient;

	public CrawlerService(FourchanChanResourceClient fourchanChanFeignClient) {
		this.chanFeignClient = fourchanChanFeignClient;
	}

	public List<ThreadResponse> crawlBoards(List<String> boards) {
		logger.info("Crawling through catalogs:: {}", boards);
		List<CatalogResponse> pages = getCatalogs(boards);
		List<ThreadResponse> threads = getThreads(pages);
		return threads;
	}

	private List<CatalogResponse> getCatalogs(List<String> boards) {
		List<CatalogResponse> catalogs = new ArrayList<>();

		for (String board : boards) {
			catalogs.addAll(getCatalogFromBoard(board));
		}

		return catalogs;
	}

	private List<CatalogResponse> getCatalogFromBoard(String board) {
		List<CatalogResponse> catalogPages = null;

		try {
			catalogPages = this.chanFeignClient.getThreadsFromBoard(board);
		} catch (ChanchanClientException e) {
			logger.error("Couldn't find board {}", board);
		}

		return catalogPages;
	}

	private List<ThreadResponse> getThreads(List<CatalogResponse> pages) {
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

	private Stream<ThreadResponse> flatMapToThreads(CatalogResponse c) {
		return c.getThreads().stream();
	}

	private List<PostResponse> getPosts(ThreadResponse t) {
		List<PostResponse> posts = null;

		try {
			posts = this.chanFeignClient.getPostsFromBoardAndThreadNumber(t.getBoard(), t.getNumber());
		} catch (ChanchanClientException e) {
			logger.error("Couldn't find posts on thread {}", t.getNumber());
		}

		return posts;
	}

}