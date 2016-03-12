package com.hugoltsp.chanchan.crawlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

@Component
public class CatalogCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(CatalogCrawler.class);

	private Collection<String> threadUrls;

	public CatalogCrawler() {
		this.threadUrls = new ArrayList<>();
	}

	public Collection<String> getThreadUrls() {
		return Collections.unmodifiableCollection(this.threadUrls);
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		return super.shouldVisit(referringPage, url);
	}

	@Override
	public void visit(Page page) {
		// TODO Auto-generated method stub
		super.visit(page);
	}

}