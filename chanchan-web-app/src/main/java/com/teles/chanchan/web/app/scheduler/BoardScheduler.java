package com.teles.chanchan.web.app.scheduler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.document.ChanBoard;
import com.teles.chanchan.fourchan.api.client.FourchanChanClient;
import com.teles.chanchan.fourchan.api.client.dto.response.BoardResponse;
import com.teles.chanchan.web.app.service.BoardService;

@Component
public class BoardScheduler {

	private static final Logger logger = LoggerFactory.getLogger(BoardScheduler.class);

	private static final int DAY = 1000 * 60 * 60 * 24;

	private final BoardService boardService;
	private final FourchanChanClient fourchanChanResourceClient;

	public BoardScheduler(BoardService boardService, FourchanChanClient fourchanChanResourceClient) {
		this.boardService = boardService;
		this.fourchanChanResourceClient = fourchanChanResourceClient;
	}

	@Scheduled(fixedDelay = DAY)
	public void updateBoards() {
		logger.info("Updating boards.");
		List<BoardResponse> allBoards = this.fourchanChanResourceClient.getAllBoards();
		saveBoardsIfNotFound(allBoards);
	}

	private void saveBoardsIfNotFound(List<BoardResponse> allBoards) {
		Set<String> boards = this.boardService.findAll().stream().map(ChanBoard::getBoard).collect(Collectors.toSet());
		allBoards.stream().filter(b -> !boards.contains(b.getBoard())).forEach(this.boardService::create);
	}

}