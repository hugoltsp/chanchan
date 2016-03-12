package com.hugoltsp.chanchan.crawlers;

import java.net.MalformedURLException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hugoltsp.chanchan.exception.ImageDownloadException;
import com.hugoltsp.chanchan.service.ImageDownloader;
import com.hugoltsp.chanchan.service.ImageWriter;
import com.hugoltsp.chanchan.utils.Image;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

@Component
public class ThreadCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(ThreadCrawler.class);

	@Inject
	private ImageDownloader downloader;
	
	@Inject
	private ImageWriter writer;

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();

		try {
			Image image = downloader.downloadImageFromUrl(href);
			logger.info("Downloading image at::{}", href);
			writer.writeImage(image);
		} catch (ImageDownloadException e) {
			logger.debug("Could not download board image at the following URL: {}, Error: {}", href, e);
		} catch (MalformedURLException e) {
			logger.debug("Error: ", e);
		}

		return false;
	}

}