package com.hugoltsp.chanchan.crawlers.factory;

import java.util.ArrayList;
import java.util.List;

import com.hugoltsp.chanchan.crawlers.ChanchanCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController.WebCrawlerFactory;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

/**
 * @author hugo
 *	 simple WebCrawlerFactory for user or container instantiated web crawlers
 */
public class ChanchanWebCrawlerFactory implements WebCrawlerFactory<ChanchanCrawler> {

	private List<ChanchanCrawler> crawlers;

	public ChanchanWebCrawlerFactory() {
		this.crawlers = new ArrayList<>();
	}

	@Override
	public ChanchanCrawler newInstance() throws Exception {
		
		return null;
	}
	
	public List<String> getUrls(){
		
		return null;
	}

}
