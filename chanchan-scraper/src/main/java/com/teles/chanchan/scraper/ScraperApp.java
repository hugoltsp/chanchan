package com.teles.chanchan.scraper;

import java.util.ArrayList;
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
import org.springframework.context.annotation.Import;

import com.teles.chanchan.domain.client.fourchan.FourchanPost;
import com.teles.chanchan.domain.client.fourchan.FourchanThread;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.service.CrawlerService;
import com.teles.chanchan.service.io.ChanchanDownloaderService;

@ComponentScan({ "com.teles.chanchan.service", "com.teles.chanchan.fourchan" })
@Import(value = { ChanchanSettings.class })
@SpringBootApplication
public class ScraperApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ScraperApp.class);

	private final ExecutorService executor;
	private final CrawlerService crawlerService;
	private final ChanchanDownloaderService downloaderService;
	private final ChanchanSettings settings;

	public ScraperApp(CrawlerService crawlerService, ChanchanSettings settings,
			ChanchanDownloaderService downloaderService) {
		this.crawlerService = crawlerService;
		this.settings = settings;
		this.downloaderService = downloaderService;
		this.executor = Executors.newFixedThreadPool(settings.getAsync().getThreadPoolSize());
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
		logger.info("Ouput path:: {}", this.settings.getIo().getOutputPath());

		List<FourchanThread> threads = this.crawlerService.crawl(boards);
		List<String> urls = this.extractDownloadUrls(threads);

		logger.info("{} files to download..", urls.size());
		urls.stream().map(this::createRunnable).forEach(this.executor::execute);

		this.executor.shutdown();

		while (!this.executor.isTerminated()) {
			Thread.sleep(1000);
		}
	}

	private List<String> extractDownloadUrls(List<FourchanThread> threads) {
		return threads.stream().flatMap(t -> t.getPosts().stream()).map(FourchanPost::getContentUrl)
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	private Runnable createRunnable(String url) {
		return new Runnable() {
			public void run() {
				downloaderService.downloadImage(url);
			}
		};
	}

	private List<String> parseBoards(String... args) {
		List<String> boards = new ArrayList<>();
		String collect = Stream.of(args).map(this::trimAndLowerCase).collect(Collectors.joining());

		int indexOf = collect.indexOf("-");

		if (indexOf != -1) {
			collect = collect.substring(0, indexOf);
		}

		boards.addAll(Stream.of(collect.split(",")).distinct().collect(Collectors.toList()));
		return boards;
	}

	private String trimAndLowerCase(String str) {
		return str.trim().toLowerCase();
	}

}