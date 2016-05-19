package com.hugoltsp.chanchan.crawlers.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.hugoltsp.chanchan.crawlers.ChanchanCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController.WebCrawlerFactory;

/**
 * @author hugo simple WebCrawlerFactory for user or container instantiated web
 *         crawlers
 */
public class ChanchanWebCrawlerFactory implements WebCrawlerFactory<ChanchanCrawler> {

	private List<ChanchanCrawler> crawlers;
	private Supplier<ChanchanCrawler> supplier;

	public ChanchanWebCrawlerFactory(Supplier<ChanchanCrawler> supplier) {
		this.supplier = supplier;
		this.crawlers = new ArrayList<>();
	}

	@Override
	public ChanchanCrawler newInstance() throws Exception {
		ChanchanCrawler crawler = this.supplier.get();
		this.crawlers.add(crawler);
		return crawler;
	}

	public List<String> getUrls() {
		List<String> list = new ArrayList<>();
		this.crawlers.stream().map(c -> c.getData()).forEach(list::addAll);
		return list;
	}

}
