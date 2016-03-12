package com.hugoltsp.chanchan.crawlers.factory;

import edu.uci.ics.crawler4j.crawler.CrawlController.WebCrawlerFactory;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

/**
 * @author hugo
 *	 simple WebCrawlerFactory for user or container instantiated web crawlers
 */
public class ChanchanWebCrawlerFactory implements WebCrawlerFactory<WebCrawler> {

	private final WebCrawler webCrawler;

	public ChanchanWebCrawlerFactory(WebCrawler webCrawler) {
		this.webCrawler = webCrawler;
	}

	@Override
	public WebCrawler newInstance() throws Exception {
		return this.webCrawler;
	}

}
