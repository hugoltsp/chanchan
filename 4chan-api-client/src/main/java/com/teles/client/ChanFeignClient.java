package com.teles.client;

import java.util.ArrayList;
import java.util.List;

import com.teles.client.domain.response.CatalogPage;
import com.teles.client.domain.response.Post;
import com.teles.client.domain.response.Thread;
import com.teles.client.exception.ChanClientException;

import feign.Feign;
import feign.FeignException;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class ChanFeignClient {

	private final String apiUrl;

	public ChanFeignClient(String apiUrl) throws IllegalArgumentException {
		if (apiUrl == null) {
			throw new IllegalArgumentException();
		}

		this.apiUrl = apiUrl;
	}

	public List<CatalogPage> getCatalogPages(String board) throws ChanClientException {
		List<CatalogPage> catalog = new ArrayList<>();

		try {

			ChanResource resource = createResource();
			catalog = resource.getCatalog(board);
			catalog.forEach(c -> c.setBoard(board));

		} catch (FeignException e) {
			throw new ChanClientException(e);
		}

		return catalog;
	}

	public List<Post> getPosts(Thread thread) throws ChanClientException {
		List<Post> posts = new ArrayList<>();

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