package com.teles.chanchan.scraper.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.exception.ChanchanApiClientException;
import com.teles.chanchan.dto.api.client.response.PostResponse;
import com.teles.chanchan.dto.api.client.response.SimpleThreadResponse;
import com.teles.chanchan.dto.api.client.response.ThreadResponse;
import com.teles.chanchan.fourchan.api.client.FourchanChanResourceClient;

@Service
public class ScrapperService {

	private static final Logger logger = LoggerFactory.getLogger(ScrapperService.class);

	private final FourchanChanResourceClient chanFeignClient;

	public ScrapperService(FourchanChanResourceClient fourchanChanFeignClient) {
		this.chanFeignClient = fourchanChanFeignClient;
	}

	public List<ThreadResponse> crawlThreads(List<String> boards) {
		logger.info("Crawling through boards: {}", boards);
		List<ThreadResponse> threadsFromBoards = getThreadsFromBoards(boards);
		logger.info("Total of {} threads found.", threadsFromBoards.size());
		return threadsFromBoards;
	}

	public List<PostResponse> crawlPosts(SimpleThreadResponse threadResponse) {
		logger.info("searching for posts on thread {} of {}", threadResponse.getNumber(), threadResponse.getBoard());

		List<PostResponse> posts = new ArrayList<>();

		try {

			posts.addAll(this.chanFeignClient.getPostsFromBoardAndThreadNumber(threadResponse.getBoard(),
					threadResponse.getNumber()));

		} catch (ChanchanApiClientException e) {
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
		List<ThreadResponse> threads = new ArrayList<>();

		try {

			threads.addAll(this.chanFeignClient.getThreadsFromBoard(board));

		} catch (ChanchanApiClientException e) {
			logger.error("Couldn't find board {}", board);
		}

		return threads;
	}

}