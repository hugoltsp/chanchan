package com.teles.chanchan.fourchan.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.FourchanCatalogPage;
import com.teles.chanchan.domain.FourchanPost;
import com.teles.chanchan.domain.FourchanThread;
import com.teles.chanchan.domain.exception.ChanchanClientException;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.fourchan.client.url.PostContentUrlResolver;

import feign.Feign;
import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

@Component
public class FourchanChanFeignClient {

	private final PostContentUrlResolver imageUrlResolver;
	private final ChanResource resource;

	public FourchanChanFeignClient(PostContentUrlResolver imageUrlResolver, ChanchanSettings settings) {
		this.imageUrlResolver = imageUrlResolver;
		this.resource = createResource(settings.getClientFourChan().getApiUrl());
	}

	public List<FourchanCatalogPage> getCatalogPages(String board) throws ChanchanClientException {
		List<FourchanCatalogPage> catalog = new ArrayList<>();

		try {

			catalog.addAll(this.resource.getCatalog(board));

			for (FourchanCatalogPage catalogPage : catalog) {
				catalogPage.setBoard(board);
			}

		} catch (FeignException e) {
			throw new ChanchanClientException(e.status(), e);
		}

		return catalog;
	}

	public List<FourchanPost> getPosts(FourchanThread thread) throws ChanchanClientException {
		List<FourchanPost> posts = new ArrayList<>();

		try {

			String board = thread.getBoard();
			posts.addAll(this.resource.getThreadPosts(board, thread.getNumber()).getPosts());

			for (FourchanPost post : posts) {
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

	private ChanResource createResource(String apiUrl) {
		return Feign.builder().decoder(new JacksonDecoder()).encoder(new JacksonEncoder()).client(new OkHttpClient())
				.target(ChanResource.class, apiUrl);
	}
}