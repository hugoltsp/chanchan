package com.hugoltsp.chanchan.crawlers;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class ThreadCrawler extends WebCrawler {

	private static final String OUT_DIR = "/home/hugo/chanchan/out/wallpapers/";

	private static final Logger logger = LoggerFactory.getLogger(ThreadCrawler.class);

	private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		String seedUrl = referringPage.getWebURL().getURL();

		if (IMAGE_EXTENSIONS.matcher(href).matches()) {
//			System.out.println("HREF: " + href);
			return true;
		}

		return true;
	}

	@Override
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