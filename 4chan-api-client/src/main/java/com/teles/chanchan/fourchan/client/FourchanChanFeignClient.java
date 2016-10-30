package com.teles.chanchan.fourchan.client;

import java.util.ArrayList;
import java.util.List;

import com.teles.chanchan.domain.fourchan.FourchanCatalogPage;
import com.teles.chanchan.domain.fourchan.FourchanPost;
import com.teles.chanchan.domain.fourchan.FourchanThread;
import com.teles.chanchan.fourchan.client.exception.ChanClientException;

import feign.Feign;
import feign.FeignException;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class FourchanChanFeignClient {

	private final String apiUrl;

	public FourchanChanFeignClient(String apiUrl) throws IllegalArgumentException {
		if (apiUrl == null) {
			throw new IllegalArgumentException();
		}

		this.apiUrl = apiUrl;
	}

	public List<FourchanCatalogPage> getCatalogPages(String board) throws ChanClientException {
		List<FourchanCatalogPage> catalog = new ArrayList<>();

		try {

			ChanResource resource = createResource();
			catalog = resource.getCatalog(board);
			catalog.forEach(c -> c.setBoard(board));

		} catch (FeignException e) {
			throw new ChanClientException(e);
		}

		return catalog;
	}

	public List<FourchanPost> getPosts(FourchanThread thread) throws ChanClientException {
		List<FourchanPost> posts = new ArrayList<>();

		try {

			ChanResource resource = createResource();
			posts = resource.getThreadPosts(thread.getBoard(), thread.getNumber()).getPosts();
			posts.forEach(p -> p.setBoard(thread.getBoard()));
		
		} catch (FeignException e) {
			throw new ChanClientException(e);
		}

		return posts;
	}

	private ChanResource createResource() {
		return Feign.builder().decoder(new JacksonDecoder()).encoder(new JacksonEncoder())
				.client(new feign.okhttp.OkHttpClient()).logLevel(Level.FULL).target(ChanResource.class, this.apiUrl);
	}
}