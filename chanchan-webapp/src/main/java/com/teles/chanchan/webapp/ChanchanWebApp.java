package com.teles.chanchan.webapp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.teles.chanchan.data.repository.FourchanThreadRepository;
import com.teles.chanchan.domain.response.BoardResponse;
import com.teles.chanchan.domain.response.ThreadResponse;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.fourchan.api.client.FourchanChanResourceClient;
import com.teles.chanchan.service.CrawlerService;

@ComponentScan({ "com.teles.chanchan" })
@Import(value = { ChanchanSettings.class })
@SpringBootApplication
@EnableMongoRepositories("com.teles.chanchan.data.repository")
public class ChanchanWebApp implements CommandLineRunner{

	@Autowired
	private FourchanThreadRepository fourchanThreadRepository;
	
	@Autowired
	private FourchanChanResourceClient fourchanChanFeignClient;
	
	@Autowired
	private CrawlerService crawlerService;
	
	public static void main(String[] args) {
		SpringApplication.run(ChanchanWebApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<String> boards = this.fourchanChanFeignClient.getAllBoards().stream().map(BoardResponse::getBoard)
				.collect(Collectors.toList());

		List<ThreadResponse> threads = this.crawlerService.crawlBoards(boards);

		this.fourchanThreadRepository.save(threads);
		
	}

}
