package com.hugoltsp.chanchan.crawlers;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hugoltsp.chanchan.exception.ImageDownloadException;
import com.hugoltsp.chanchan.utils.Image;
import com.hugoltsp.chanchan.utils.ImageDownloader;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public class ThreadCrawler extends WebCrawler {

	private static final String OUT_DIR = "/home/hugo/chanchan/out/wallpapers/";

	private static final Logger logger = LoggerFactory.getLogger(ThreadCrawler.class);

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();

		try {
			logger.info("Downloading image at::{}", href);
			Image image = ImageDownloader.downloadImageFromUrl(href);

		} catch (ImageDownloadException e) {
			logger.debug("Could not download board image at the following URL: {}, Error: {}", href, e);
		} catch (MalformedURLException e) {
			logger.debug("Error: ", e);
		}

		return false;
	}

	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		System.out.println("URL:: " + url);
	}

}