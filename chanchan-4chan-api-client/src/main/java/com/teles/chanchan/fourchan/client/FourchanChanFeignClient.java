package com.teles.chanchan.fourchan.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.client.fourchan.FourchanCatalogPage;
import com.teles.chanchan.domain.client.fourchan.FourchanPost;
import com.teles.chanchan.domain.client.fourchan.FourchanThread;
import com.teles.chanchan.domain.exception.ChanClientException;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.fourchan.client.url.PostContentUrlResolver;

import feign.Feign;
import feign.FeignException;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Component
public class FourchanChanFeignClient {

	private final PostContentUrlResolver imageUrlResolver;
	private final ChanchanSettings settings;

	public FourchanChanFeignClient(PostContentUrlResolver imageUrlResolver, ChanchanSettings settings) {
		this.imageUrlResolver = imageUrlResolver;
		this.settings = settings;
	}

	public List<FourchanCatalogPage> getCatalogPages(String board) throws ChanClientException {
		List<FourchanCatalogPage> catalog = new ArrayList<>();

		try {

			ChanResource resource = createResource();
			catalog = resource.getCatalog(board);

			for (int i = 0; i < catalog.size(); i++) {
				FourchanCatalogPage c = catalog.get(i);
				c.setBoard(board);
			}

		} catch (FeignException e) {
			throw new ChanClientException(e);
		}

		return catalog;
	}

	public List<FourchanPost> getPosts(FourchanThread thread) throws ChanClientException {
		List<FourchanPost> posts = new ArrayList<>();

		try {

			String board = thread.getBoard();
			ChanResource resource = createResource();
			posts = resource.getThreadPosts(board, thread.getNumber()).getPosts();

			for (int i = 0; i < posts.size(); i++) {
				FourchanPost p = posts.get(i);
				p.setBoard(board);

				if (p.getFileExtension() != null && p.getTimeStamp() != 0) {
					p.setThumbUrl(this.imageUrlResolver.getThumbUrl(p));
					p.setContentUrl(this.imageUrlResolver.getImageUrl(p));
				}
			}

		} catch (FeignException e) {
			throw new ChanClientException(e);
		}

		return posts;
	}

	private ChanResource createResource() {
		return Feign.builder().decoder(new JacksonDecoder()).encoder(new JacksonEncoder())
				.client(new feign.okhttp.OkHttpClient()).logLevel(Level.FULL).target(ChanResource.class, this.settings.getClientFourChan().getApiUrl());
	}
}