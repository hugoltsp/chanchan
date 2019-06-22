package com.teles.chanchan.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.scraper.api.client.FourchanClient;
import com.teles.chanchan.scraper.api.client.response.PostResponse;
import com.teles.chanchan.scraper.api.client.response.SimpleThreadResponse;
import com.teles.chanchan.scraper.api.client.response.ThreadResponse;

@Service
public class ScrapperService {

	private static final Logger logger = LoggerFactory.getLogger(ScrapperService.class);

	private final FourchanClient chanClient;

	public ScrapperService(FourchanClient chanClient) {
		this.chanClient = chanClient;
	}

	public List<PostResponse> crawlBoards(List<String> boards) {
		logger.info("Crawling through boards: {}", boards);
		List<ThreadResponse> threadsFromBoards = getThreadsFromBoards(boards);
		logger.info("Total of {} threads found.", threadsFromBoards.size());
		return threadsFromBoards.parallelStream().flatMap(t -> this.crawlPosts(t).stream())
				.collect(Collectors.toList());
	}

	private List<PostResponse> crawlPosts(SimpleThreadResponse threadResponse) {

		return crawlPosts(threadResponse.getBoard(), threadResponse.getNumber());
	}

	public List<PostResponse> crawlPosts(String board, int number) {
		logger.info("searching for posts on thread {} of {}", number, board);

		return filterPostResponse(chanClient.getPostsFromBoardAndThreadNumber(board, number));
	}

	private List<ThreadResponse> getThreadsFromBoards(List<String> boards) {
		List<ThreadResponse> threads = new ArrayList<>();

		for (String board : boards) {
			threads.addAll(this.chanClient.getThreadsFromBoard(board));
		}

		return threads;
	}

	private static List<PostResponse> filterPostResponse(List<PostResponse> posts) {
		return posts.stream().filter(PostResponse::hasMedia).collect(Collectors.toList());
	}
}