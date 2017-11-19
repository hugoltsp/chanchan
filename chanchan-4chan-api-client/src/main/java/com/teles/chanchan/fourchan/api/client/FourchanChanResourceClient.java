package com.teles.chanchan.fourchan.api.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.teles.chanchan.config.settings.Client4ChanSettings;
import com.teles.chanchan.domain.exception.ChanchanApiClientException;
import com.teles.chanchan.dto.api.client.response.BoardResponse;
import com.teles.chanchan.dto.api.client.response.PostResponse;
import com.teles.chanchan.dto.api.client.response.SimpleThreadResponse;
import com.teles.chanchan.dto.api.client.response.ThreadResponse;
import com.teles.chanchan.fourchan.api.client.FourchanChanResource.ThreadsResponse;
import com.teles.chanchan.fourchan.api.client.content.ContentUrlResolver;

import feign.Feign;
import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

@Component
public class FourchanChanResourceClient {

	private final ContentUrlResolver imageUrlResolver;
	private final FourchanChanResource resource;

	public FourchanChanResourceClient(ContentUrlResolver imageUrlResolver, Client4ChanSettings settings) {
		this.imageUrlResolver = imageUrlResolver;
		this.resource = createResource(settings.getApiUrl());
	}

	public List<BoardResponse> getAllBoards() {
		List<BoardResponse> boards = new ArrayList<>();

		try {

			boards.addAll(this.resource.geAllBoards().getBoards());

		} catch (FeignException e) {
			throw new ChanchanApiClientException(e.status(), e);
		}

		return boards;
	}

	public List<ThreadResponse> getThreadsFromBoard(String board) throws ChanchanApiClientException {
		List<ThreadResponse> threads = new ArrayList<>();

		try {

			this.resource.getCatalog(board).stream().flatMap(c -> c.getThreads().stream()).forEach(t -> {
				t.setBoard(board);
				threads.add(t);
			});

		} catch (FeignException e) {
			throw new ChanchanApiClientException(e.status(), e);
		}

		return threads;
	}

	public List<PostResponse> getPostsFromBoardAndThreadNumber(String board, int threadNumber)
			throws ChanchanApiClientException {
		List<PostResponse> posts = new ArrayList<>();

		try {

			for (PostResponse post : this.resource.getPosts(board, threadNumber).getPosts()) {
				post.setBoard(board);

				if (this.imageUrlResolver.hasMedia(post)) {
					post.setThumbUrl(this.imageUrlResolver.buildThumbNailUrl(post));
					post.setContentUrl(this.imageUrlResolver.buildMediaUrl(post));
				}

				posts.add(post);
			}

		} catch (FeignException e) {
			throw new ChanchanApiClientException(e.status(), e);
		}

		return posts;
	}

	public List<SimpleThreadResponse> getSimpleThreadsFromBoard(String board) {
		List<SimpleThreadResponse> simpleThreadResponse = new ArrayList<>();

		try {

			for (ThreadsResponse threadsResponse : this.resource.getThreads(board)) {
				simpleThreadResponse.addAll(threadsResponse.getThreads());
			}

		} catch (FeignException e) {
			throw new ChanchanApiClientException(e.status(), e);
		}

		return simpleThreadResponse;
	}

	private FourchanChanResource createResource(String apiUrl) {
		return Feign.builder().decoder(new JacksonDecoder()).encoder(new JacksonEncoder()).client(new OkHttpClient())
				.target(FourchanChanResource.class, apiUrl);
	}
}