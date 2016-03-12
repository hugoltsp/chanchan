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

	private static final String THREAD_RESOURCE_URL = "/thread/";

	private Collection<String> threadUrls;

	private String boardResourceUrlId;

	public CatalogCrawler() {
		this.threadUrls = new ArrayList<>();
	}

	public Collection<String> getThreadUrls() {
		return Collections.unmodifiableCollection(this.threadUrls);
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {

		if (null == this.boardResourceUrlId) {
			String webUrl = referringPage.getWebURL().getPath();
			boardResourceUrlId = webUrl.substring(0, webUrl.lastIndexOf("/"));
		}

		if (url.getURL().contains(boardResourceUrlId)) {
			return true;
		}

		return false;
	}

	@Override
	public void visit(Page page) {
		String path = page.getWebURL().getPath();
		if (path.contains(this.boardResourceUrlId + THREAD_RESOURCE_URL)) {
			this.threadUrls.add(page.getWebURL().getURL());
		}
	}
	
}