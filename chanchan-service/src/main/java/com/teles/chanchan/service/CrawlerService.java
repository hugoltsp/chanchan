package com.teles.chanchan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.client.fourchan.FourchanCatalogPage;
import com.teles.chanchan.domain.client.fourchan.FourchanPost;
import com.teles.chanchan.domain.client.fourchan.FourchanThread;
import com.teles.chanchan.fourchan.client.FourchanChanFeignClient;

@Service
public class CrawlerService {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	private final FourchanChanFeignClient chanFeignClient;

	public CrawlerService(FourchanChanFeignClient fourchanChanFeignClient) {
		this.chanFeignClient = fourchanChanFeignClient;
	}

	public List<FourchanThread> crawl(List<String> catalogs) {
		logger.info("Crawling through catalogs:: {}", catalogs);
		List<FourchanCatalogPage> pages = this.pages(catalogs);
		List<FourchanThread> threads = this.threads(pages);
		return threads;
	}

	private List<FourchanCatalogPage> pages(List<String> catalogBoards){
		return catalogBoards.stream().flatMap(b->this.chanFeignClient.getCatalogPages(b).stream()).collect(Collectors.toList());
	}
	
	private List<FourchanThread> threads(List<FourchanCatalogPage> pages){
		return pages.stream().flatMap(c->c.getThreads().stream()).map(t->{
			logger.info("searching for posts on thread {} of {}", t.getNumber(), t.getBoard());
			List<FourchanPost> posts = this.chanFeignClient.getPosts(t);
			t.setPosts(posts);
			return t;
		}).collect(Collectors.toList());
	}
	
}