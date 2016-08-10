package com.hugoltsp.chanchan.crawlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class ThreadCrawler extends ChanchanCrawler {

	private static final Logger logger = LoggerFactory.getLogger(ThreadCrawler.class);

	private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");

	private final List<String> urls;

	public ThreadCrawler() {
		this.urls = new ArrayList<>();
	}

	@Override
	public Collection<String> getData() {
		return this.urls;
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();

		if (IMAGE_EXTENSIONS.matcher(url.getPath()).matches() && !url.getPath().contains("s.")) {
			urls.add(href);
		} else {
			logger.error("Unsupported URL:: " + href);
		}

		return false;
	}

}