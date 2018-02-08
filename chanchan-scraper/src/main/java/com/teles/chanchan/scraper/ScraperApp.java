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

import com.teles.chanchan.fourchan.api.client.dto.response.PostResponse;
import com.teles.chanchan.fourchan.api.client.dto.response.ThreadResponse;
import com.teles.chanchan.scraper.service.DownloaderService;
import com.teles.chanchan.scraper.service.ScrapperService;

@ComponentScan("com.teles.chanchan")
@SpringBootApplication
public class ScraperApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ScraperApp.class);

	private final ExecutorService executor;
	private final ScrapperService crawlerService;
	private final DownloaderService downloaderService;

	public ScraperApp(ScrapperService crawlerService, DownloaderService downloaderService) {
		this.crawlerService = crawlerService;
		this.downloaderService = downloaderService;
		this.executor = Executors.newFixedThreadPool(20);
	}

	public static void main(String[] args) {

		if (!isArgsValid(args)) {
			throw new IllegalArgumentException("At least one catalog must be specified");
		}

		SpringApplication.run(ScraperApp.class, args);
	}

	public void run(String... args) throws Exception {

		logger.info("Chanchan started");
		List<String> boards = parseBoards(args);

		List<ThreadResponse> threads = this.crawlerService.crawlThreads(boards);

		List<String> urls = threads.parallelStream().flatMap(t -> this.crawlerService.crawlPosts(t).stream())
				.map(PostResponse::getContentUrl).filter(Objects::nonNull).collect(Collectors.toList());

		logger.info("{} files to download..", urls.size());
		urls.stream().map(this::createRunnable).forEach(this.executor::execute);

		this.executor.shutdown();

		while (!this.executor.isTerminated()) {
			Thread.sleep(1000);
		}
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

	private static boolean isArgsValid(String... args) {
		return args != null && args.length >= 1;
	}

}