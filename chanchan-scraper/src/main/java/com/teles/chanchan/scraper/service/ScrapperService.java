package com.teles.chanchan.scraper.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.fourchan.api.client.FourchanClient;
import com.teles.chanchan.fourchan.api.client.dto.response.PostResponse;
import com.teles.chanchan.fourchan.api.client.dto.response.SimpleThreadResponse;
import com.teles.chanchan.fourchan.api.client.dto.response.ThreadResponse;

@Service
public class ScrapperService {

	private static final Logger logger = LoggerFactory.getLogger(ScrapperService.class);

	private final FourchanClient chanClient;

	public ScrapperService(FourchanClient chanClient) {
		this.chanClient = chanClient;
	}

	public List<ThreadResponse> crawlThreads(List<String> boards) {
		logger.info("Crawling through boards: {}", boards);
		List<ThreadResponse> threadsFromBoards = getThreadsFromBoards(boards);
		logger.info("Total of {} threads found.", threadsFromBoards.size());
		return threadsFromBoards;
	}

	public List<PostResponse> crawlPosts(SimpleThreadResponse threadResponse) {
		logger.info("searching for posts on thread {} of {}", threadResponse.getNumber(), threadResponse.getBoard());

		return this.chanClient.getPostsFromBoardAndThreadNumber(threadResponse.getBoard(),
				threadResponse.getNumber());
	}

	private List<ThreadResponse> getThreadsFromBoards(List<String> boards) {
		List<ThreadResponse> threads = new ArrayList<>();

		for (String board : boards) {
			threads.addAll(this.chanClient.getThreadsFromBoard(board));
		}

		return threads;
	}

}