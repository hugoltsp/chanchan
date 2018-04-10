package com.teles.chanchan.scraper;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.teles.chanchan.fourchan.api.client.dto.response.PostResponse;
import com.teles.chanchan.scraper.service.DownloaderService;
import com.teles.chanchan.scraper.service.ScrapperService;

@ComponentScan("com.teles.chanchan")
@SpringBootApplication
public class ScraperApp implements CommandLineRunner {

	private static final String BOARDS_MODE_ARG = "b";
	private static final String THREADS_MODE_ARG = "t";

	private static final String BOARDS_ARG = "boards";
	private static final String BOARD_ARG = "board";
	private static final String THREAD_ARG = "thread";

	private static final Logger logger = LoggerFactory.getLogger(ScraperApp.class);

	private static final Options OPTIONS = buildOptions();

	private final ExecutorService executor;
	private final ScrapperService crawlerService;
	private final DownloaderService downloaderService;

	public ScraperApp(ScrapperService crawlerService, DownloaderService downloaderService) {
		this.crawlerService = crawlerService;
		this.downloaderService = downloaderService;
		this.executor = Executors.newFixedThreadPool(20);
	}

	public static void main(String[] args) {
		SpringApplication.run(ScraperApp.class, args);
	}

	public void run(String... args) {

		logger.info("Chanchan started");

		try {

			CommandLine commandLine = new DefaultParser().parse(OPTIONS, args, true);

			if (!isCommandLineValid(commandLine)) {
				throw new IllegalArgumentException(
						format("Use --%s to download entire boards OR --%s to download files from a single thread.",
								BOARDS_MODE_ARG, THREADS_MODE_ARG));
			}

			List<PostResponse> posts = new ArrayList<>();

			if (commandLine.hasOption(BOARDS_MODE_ARG)) {

				posts.addAll(crawlerService.crawlBoards(parseBoards(commandLine.getOptionValue(BOARDS_ARG))));

			} else {

				posts.addAll(crawlerService.crawlPosts(parseBoard(commandLine.getOptionValue(BOARD_ARG)),
						parseThread(commandLine.getOptionValue(THREAD_ARG))));

			}

			logger.info("{} files to download..", posts.size());

			posts.stream().map(this::createRunnable).forEach(this.executor::execute);

			this.executor.shutdown();

			while (!this.executor.isTerminated()) {
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			logger.error("Error", e);
		}

	}

	private Runnable createRunnable(PostResponse post) {
		return () -> downloaderService.downloadImage(post);
	}

	private static Integer parseThread(String thread) {

		if (isBlank(thread) || !isNumeric(thread)) {
			throw new IllegalArgumentException(format("Parameter --%s is required", THREAD_ARG));
		}

		return Integer.valueOf(thread);
	}

	private static List<String> parseBoards(String boards) {

		if (isBlank(boards)) {
			throw new IllegalArgumentException(
					format("At least one board must be specified (ex --%s=wg,ck,fit)", BOARDS_ARG));
		}

		return Stream.of(trimAndLowerCase(boards).split(",")).distinct().collect(Collectors.toList());
	}

	private static String parseBoard(String board) {

		if (isBlank(board)) {
			throw new IllegalArgumentException(format("Parameter --%s is required", BOARD_ARG));
		}

		return trimAndLowerCase(board);
	}

	private static String trimAndLowerCase(String str) {
		return str.trim().toLowerCase();
	}

	private static Options buildOptions() {
		return new Options().addOption(Option.builder().longOpt(BOARDS_ARG).hasArg().build())
				.addOption(Option.builder().longOpt(BOARD_ARG).hasArg().build())
				.addOption(Option.builder().longOpt(THREAD_ARG).hasArg().build())
				.addOption(Option.builder().longOpt(BOARDS_MODE_ARG).build())
				.addOption(Option.builder().longOpt(THREADS_MODE_ARG).build());
	}

	private static boolean isCommandLineValid(CommandLine commandLine) {
		return !(commandLine.hasOption(BOARDS_MODE_ARG) == commandLine.hasOption(THREADS_MODE_ARG));
	}
}