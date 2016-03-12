package com.hugoltsp.chanchan.crawlers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.hugoltsp.chanchan.exception.ImageDownloadException;
import com.hugoltsp.chanchan.utils.ImageDownloader;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class ThreadCrawler extends WebCrawler {

	private static final String OUT_DIR = "/home/hugo/chanchan/out/wallpapers/";

	private static final Logger logger = LoggerFactory.getLogger(ThreadCrawler.class);

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();

		try {
			ImageDownloader.downloadImageFromUrl(href);
		} catch (ImageDownloadException e) {
			logger.debug("Could not download board image at the following URL: {}, Error: {}", href, e);
		} catch (MalformedURLException e) {
			logger.debug("Error: ", e);
		}

		return false;
	}

	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		String extension = url.substring(url.lastIndexOf('.'));

		System.out.println("URL:: " + url);

		if (page.getParseData() instanceof BinaryParseData) {
			try {
				File file = new File(OUT_DIR + Instant.now().getEpochSecond() + extension);
				file.createNewFile();
				Files.write(page.getContentData(), file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}