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
import org.springframework.context.annotation.Import;

import com.teles.chanchan.domain.response.PostResponse;
import com.teles.chanchan.domain.response.ThreadResponse;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.service.CrawlerService;
import com.teles.chanchan.service.io.DownloaderService;

@ComponentScan({ "com.teles.chanchan.service", "com.teles.chanchan.fourchan" })
@Import(value = { ChanchanSettings.class })
@SpringBootApplication
public class ScraperApp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ScraperApp.class);

	private final ExecutorService executor;
	private final CrawlerService crawlerService;
	private final DownloaderService downloaderService;
	private final String outputPath;

	public ScraperApp(CrawlerService crawlerService, ChanchanSettings settings, DownloaderService downloaderService) {
		this.crawlerService = crawlerService;
		this.outputPath = settings.getIo().getOutputPath();
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
		logger.info("Ouput path:: {}", this.outputPath);

		List<ThreadResponse> threads = this.crawlerService.crawlBoards(boards);
		List<String> urls = this.extractDownloadUrls(threads);

		logger.info("{} files to download..", urls.size());
		urls.stream().map(this::createRunnable).forEach(this.executor::execute);

		this.executor.shutdown();

		while (!this.executor.isTerminated()) {
			Thread.sleep(1000);
		}
	}

	private List<String> extractDownloadUrls(List<ThreadResponse> threads) {
		return threads.stream().flatMap(t -> t.getPosts().stream()).map(PostResponse::getContentUrl)
				.filter(Objects::nonNull).collect(Collectors.toList());
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