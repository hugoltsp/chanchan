package com.hugoltsp.chanchan.crawlers;

import java.util.ArrayList;
import java.util.List;

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

	private List<String> boardResourceUrlIds;
	private List<String> threadUrls;

	public CatalogCrawler() {
		this.threadUrls = new ArrayList<>();
		this.boardResourceUrlIds = new ArrayList<>();
	}

	public List<String> getThreadUrls() {
		return threadUrls;
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String webUrl = referringPage.getWebURL().getPath();
		String boardResourceUrlId = webUrl.substring(0, webUrl.lastIndexOf("/"));

		if (!this.boardResourceUrlIds.contains(boardResourceUrlId)) {
			this.boardResourceUrlIds.add(boardResourceUrlId);
		}

		if (hasResourceId(url.getURL(), false)) {
			return true;
		}

		return false;
	}

	@Override
	public void visit(Page page) {
		String path = page.getWebURL().getPath();

		if (hasResourceId(path, true)) {
			String url = page.getWebURL().getURL();
			logger.info("Adding the following URL:: {}", url);
			this.threadUrls.add(url);
		}
	}

	private boolean hasResourceId(String searchPath, boolean isThread) {

		for (int i = 0; i < this.boardResourceUrlIds.size(); i++) {
			String boardResourceUrl = null;
			if (isThread) {
				boardResourceUrl = this.boardResourceUrlIds.get(i) + THREAD_RESOURCE_URL;
			} else {
				boardResourceUrl = this.boardResourceUrlIds.get(i);
			}
			if (searchPath.contains(boardResourceUrl)) {
				return true;
			}
		}

		return false;
	}

}