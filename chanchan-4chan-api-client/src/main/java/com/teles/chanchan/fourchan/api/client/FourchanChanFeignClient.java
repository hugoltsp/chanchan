package com.teles.chanchan.fourchan.api.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.exception.ChanchanClientException;
import com.teles.chanchan.domain.response.BoardResponse;
import com.teles.chanchan.domain.response.CatalogPageResponse;
import com.teles.chanchan.domain.response.PostResponse;
import com.teles.chanchan.domain.response.ThreadResponse;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.fourchan.api.client.content.ContentUrlResolver;

import feign.Feign;
import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

@Component
public class FourchanChanFeignClient {

	private final ContentUrlResolver imageUrlResolver;
	private final FourchanChanResource resource;

	public FourchanChanFeignClient(ContentUrlResolver imageUrlResolver, ChanchanSettings settings) {
		this.imageUrlResolver = imageUrlResolver;
		this.resource = createResource(settings.getClientFourChan().getApiUrl());
	}

	public List<BoardResponse> getBoards() {
		List<BoardResponse> boards = new ArrayList<>();

		try {

			boards.addAll(this.resource.geAllBoards().getBoards());

		} catch (FeignException e) {
			throw new ChanchanClientException(e.status(), e);
		}

		return boards;
	}

	public List<CatalogPageResponse> getCatalogPages(String board) throws ChanchanClientException {
		List<CatalogPageResponse> catalog = new ArrayList<>();

		try {

			catalog.addAll(this.resource.getCatalog(board));

			for (CatalogPageResponse catalogPage : catalog) {
				catalogPage.setBoard(board);
			}

		} catch (FeignException e) {
			throw new ChanchanClientException(e.status(), e);
		}

		return catalog;
	}

	public List<PostResponse> getPosts(ThreadResponse thread) throws ChanchanClientException {
		List<PostResponse> posts = new ArrayList<>();

		try {

			String board = thread.getBoard();
			posts.addAll(this.resource.getThreadPosts(board, thread.getNumber()).getPosts());

			for (PostResponse post : posts) {
				post.setBoard(board);

				if (this.imageUrlResolver.hasMedia(post)) {
					post.setThumbUrl(this.imageUrlResolver.buildThumbNailUrl(post));
					post.setContentUrl(this.imageUrlResolver.buildMediaUrl(post));
				}
			}

		} catch (FeignException e) {
			throw new ChanchanClientException(e.status(), e);
		}

		return posts;
	}

	private FourchanChanResource createResource(String apiUrl) {
		return Feign.builder().decoder(new JacksonDecoder()).encoder(new JacksonEncoder()).client(new OkHttpClient())
				.target(FourchanChanResource.class, apiUrl);
	}
}