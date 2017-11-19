package com.teles.chanchan.scraper;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.teles.chanchan.config.settings.AsyncSettings;
import com.teles.chanchan.dto.api.client.response.PostResponse;
import com.teles.chanchan.dto.api.client.response.ThreadResponse;
import com.teles.chanchan.scraper.service.DownloaderService;
import com.teles.chanchan.scraper.service.ScrapperService;

@ComponentScan("com.teles.chanchan")
@SpringBootApplication
public class ScraperApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ScraperApp.class);

	private final ExecutorService executor;
	private final ScrapperService crawlerService;
	private final DownloaderService downloaderService;

	public ScraperApp(ScrapperService crawlerService, AsyncSettings asyncSettings,
			DownloaderService downloaderService) {
		this.crawlerService = crawlerService;
		this.downloaderService = downloaderService;
		this.executor = Executors.newFixedThreadPool(asyncSettings.getThreadPoolSize());
	}

	public static void main(String[] args) {
		SpringApplication.run(ScraperApp.class, args);
	}

	public void run(String... args) throws Exception {
		if (args == null || args.length < 1) {
			throw new IllegalArgumentException("At least one catalog must be specified");
		}

		logger.info("Chanchan started");
		List<String> boards = this.parseBoards(args);

		logger.info("Boards:: {}", boards);

		List<ThreadResponse> threads = this.crawlerService.crawlThreads(boards);

		List<PostResponse> posts = threads.parallelStream().flatMap(t -> this.crawlerService.crawlPosts(t).stream())
				.collect(Collectors.toList());

		List<String> urls = this.extractDownloadUrls(posts);

		logger.info("{} files to download..", urls.size());
		urls.stream().map(this::createRunnable).forEach(this.executor::execute);

		this.executor.shutdown();

		while (!this.executor.isTerminated()) {
			Thread.sleep(1000);
		}
	}

	private List<String> extractDownloadUrls(List<PostResponse> posts) {
		return posts.stream().map(PostResponse::getContentUrl).filter(Objects::nonNull).collect(Collectors.toList());
	}

	private Runnable createRunnable(String url) {
		return () -> downloaderService.downloadImage(url);
	}

	private List<String> parseBoards(String... args) {
		String collect = Stream.of(args).map(this::trimAndLowerCase).collect(Collectors.joining());

		int indexOf = collect.indexOf("-");

		if (indexOf != -1) {
			collect = collect.substring(0, indexOf);
		}

		return Stream.of(collect.split(",")).distinct().collect(Collectors.toList());
	}

	private String trimAndLowerCase(String str) {
		return str.trim().toLowerCase();
	}

}