package com.hugoltsp.chanchan.crawlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class CatalogCrawler extends ChanchanCrawler {

	private static final Logger logger = LoggerFactory.getLogger(CatalogCrawler.class);

	private static final String THREAD_RESOURCE_URL = "/thread/";

	private final Set<String> boardResourceUrlIds;
	private final List<String> threadUrls;

	public CatalogCrawler() {
		this.threadUrls = new ArrayList<>();
		this.boardResourceUrlIds = new HashSet<>();
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String webUrl = referringPage.getWebURL().getPath();
		String boardResourceUrlId = webUrl.substring(0, webUrl.lastIndexOf("/"));

		this.boardResourceUrlIds.add(boardResourceUrlId);

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
		return this.boardResourceUrlIds.stream().map(b -> {
			if (isThread) {
				return b + THREAD_RESOURCE_URL;
			}
			return b;
		}).anyMatch(searchPath::contains);
	}

	@Override
	public Collection<String> getData() {
		return this.threadUrls;
	}

}