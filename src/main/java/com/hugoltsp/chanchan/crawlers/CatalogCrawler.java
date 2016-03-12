package com.hugoltsp.chanchan.crawlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	private Collection<String> threadUrls;

	public CatalogCrawler() {
		this.threadUrls = new ArrayList<>();
		this.boardResourceUrlIds = new ArrayList<>();
	}

	public Collection<String> getThreadUrls() {
		return Collections.unmodifiableCollection(this.threadUrls);
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String webUrl = referringPage.getWebURL().getPath();
		String boardResourceUrlId = webUrl.substring(0, webUrl.lastIndexOf("/"));

		if (!this.boardResourceUrlIds.contains(boardResourceUrlId)) {
			this.boardResourceUrlIds.add(boardResourceUrlId);
		}

		if (hasResourceId(url.getURL(), this.boardResourceUrlIds, false)) {
			return true;
		}

		return false;
	}

	@Override
	public void visit(Page page) {
		String path = page.getWebURL().getPath();

		if (hasResourceId(path, this.boardResourceUrlIds, true)) {
			String url = page.getWebURL().getURL();
			logger.info("Adding the following URL:: {}", url);
			this.threadUrls.add(url);
		}
	}

	private static boolean hasResourceId(String search, List<String> boardResourceUrls, boolean isThread) {

		for (int i = 0; i < boardResourceUrls.size(); i++) {
			String boardResourceUrl = null;
			if (isThread) {
				boardResourceUrl = boardResourceUrls.get(i) + THREAD_RESOURCE_URL;
			} else {
				boardResourceUrl = boardResourceUrls.get(i);
			}
			if (search.contains(boardResourceUrl)) {
				return true;
			}
		}

		return false;
	}

}