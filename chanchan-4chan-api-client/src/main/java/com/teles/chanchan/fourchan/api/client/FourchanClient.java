package com.teles.chanchan.fourchan.api.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.teles.chanchan.config.settings.Client4ChanSettings;
import com.teles.chanchan.fourchan.api.client.FourchanResource.ThreadsResponse;
import com.teles.chanchan.fourchan.api.client.content.ContentUrlResolver;
import com.teles.chanchan.fourchan.api.client.dto.response.BoardResponse;
import com.teles.chanchan.fourchan.api.client.dto.response.PostResponse;
import com.teles.chanchan.fourchan.api.client.dto.response.SimpleThreadResponse;
import com.teles.chanchan.fourchan.api.client.dto.response.ThreadResponse;

import feign.Feign;
import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

@Component
public class FourchanClient {

	private static final Logger logger = LoggerFactory.getLogger(FourchanClient.class);

	private final ContentUrlResolver imageUrlResolver;
	private final FourchanResource resource;

	public FourchanClient(ContentUrlResolver imageUrlResolver, Client4ChanSettings settings) {
		this.imageUrlResolver = imageUrlResolver;
		this.resource = createResource(settings.getApiUrl());
	}

	public List<BoardResponse> getAllBoards() {
		List<BoardResponse> boards = new ArrayList<>();

		try {

			boards.addAll(this.resource.geAllBoards().getBoards());

		} catch (FeignException e) {
			logger.error("Unable to request resource", e);
		}

		logger.debug("{} boards found.", boards.size());

		return boards;
	}

	public List<ThreadResponse> getThreadsFromBoard(String board) {
		List<ThreadResponse> threads = new ArrayList<>();

		try {

			this.resource.getCatalog(board).stream().flatMap(c -> c.getThreads().stream()).forEach(t -> {
				t.setBoard(board);
				threads.add(t);
			});

		} catch (FeignException e) {
			logger.error("Unable to request resource", e);
		}

		logger.debug("{} threads found on board {}.", threads.size(), board);

		return threads;
	}

	public List<PostResponse> getPostsFromBoardAndThreadNumber(String board, int threadNumber) {
		List<PostResponse> posts = new ArrayList<>();

		try {

			for (PostResponse post : this.resource.getPosts(board, threadNumber).getPosts()) {
				post.setBoard(board);

				if (post.hasMedia()) {
					post.setThumbUrl(this.imageUrlResolver.buildThumbNailUrl(post));
					post.setContentUrl(this.imageUrlResolver.buildMediaUrl(post));
				}

				posts.add(post);
			}

		} catch (FeignException e) {
			logger.error("Unable to request resource", e);
		}

		logger.debug("{} posts found on thread {}", posts.size(), threadNumber);
		
		return posts;
	}

	public List<SimpleThreadResponse> getSimpleThreadsFromBoard(String board) {
		List<SimpleThreadResponse> simpleThreadResponse = new ArrayList<>();

		try {

			for (ThreadsResponse threadsResponse : this.resource.getThreads(board)) {
				simpleThreadResponse.addAll(threadsResponse.getThreads());
			}

		} catch (FeignException e) {
			logger.error("Unable to request resource", e);
		}
		
		logger.debug("{} threads found on board {}.", simpleThreadResponse.size(), board);

		return simpleThreadResponse;
	}

	private static FourchanResource createResource(String apiUrl) {
		return Feign.builder().decoder(new JacksonDecoder()).encoder(new JacksonEncoder()).client(new OkHttpClient())
				.target(FourchanResource.class, apiUrl);
	}
}