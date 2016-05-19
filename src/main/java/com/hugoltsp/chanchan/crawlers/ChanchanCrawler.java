package com.hugoltsp.chanchan.crawlers;

import java.util.Collection;

import edu.uci.ics.crawler4j.crawler.WebCrawler;

public abstract class ChanchanCrawler extends WebCrawler {

	public abstract Collection<? extends String> getData();

}