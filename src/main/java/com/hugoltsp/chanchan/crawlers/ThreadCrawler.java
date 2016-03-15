package com.hugoltsp.chanchan.crawlers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hugoltsp.chanchan.service.ImageService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

@Component
public class ThreadCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(ThreadCrawler.class);

	@Inject
	private ImageService imageService;

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();

		try {
			this.imageService.download(href);
		} catch (Exception e) {
			logger.error("An error ocurred while trying to write the image on disk: {}", e);
		}

		return false;
	}

}