package com.teles.chanchan.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

	public List<ThreadResponse> crawlThreads(List<String> boards) {
		logger.info("Crawling through boards: {}", boards);
		List<ThreadResponse> threadsFromBoards = getThreadsFromBoards(boards);
		logger.info("Total of {} threads found.", threadsFromBoards.size());
		return threadsFromBoards;
	}

	public List<PostResponse> crawlPosts(ThreadResponse threadResponse) {
		logger.info("searching for posts on thread {} of {}", threadResponse.getNumber(), threadResponse.getBoard());

		List<PostResponse> posts = null;

		try {

			posts = this.chanFeignClient.getPostsFromBoardAndThreadNumber(threadResponse.getBoard(),
					threadResponse.getNumber());

		} catch (ChanchanClientException e) {
			logger.error("Couldn't find posts on thread {}", threadResponse.getNumber());
		}

		return posts;
	}

	private List<ThreadResponse> getThreadsFromBoards(List<String> boards) {
		List<ThreadResponse> threads = new ArrayList<>();

		for (String board : boards) {
			List<ThreadResponse> threadsFromBoard = getThreadsFromBoard(board);
			logger.info("Total of {} threads found on board {}.", threadsFromBoard.size(), board);
			threads.addAll(threadsFromBoard);
		}

		return threads;
	}

	private List<ThreadResponse> getThreadsFromBoard(String board) {
		List<ThreadResponse> catalogPages = null;

		try {

			catalogPages = this.chanFeignClient.getThreadsFromBoard(board);

		} catch (ChanchanClientException e) {
			logger.error("Couldn't find board {}", board);
		}

		return catalogPages;
	}

}