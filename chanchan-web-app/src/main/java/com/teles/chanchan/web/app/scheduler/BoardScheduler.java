package com.teles.chanchan.web.app.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.document.ChanBoard;
import com.teles.chanchan.fourchan.api.client.FourchanClient;
import com.teles.chanchan.fourchan.api.client.dto.response.BoardResponse;
import com.teles.chanchan.web.app.service.BoardService;

@Component
public class BoardScheduler {

	private static final Logger logger = LoggerFactory.getLogger(BoardScheduler.class);

	private static final int DAY = 1000 * 60 * 60 * 24;

	private final BoardService boardService;
	private final FourchanClient fourchanChanClient;

	public BoardScheduler(BoardService boardService, FourchanClient fourchanChanResourceClient) {
		this.boardService = boardService;
		this.fourchanChanClient = fourchanChanResourceClient;
	}

	@Scheduled(fixedDelay = DAY)
	public void updateBoards() {
		logger.info("Updating boards.");
		saveAll(this.fourchanChanClient.getAllBoards());
	}

	private void saveAll(List<BoardResponse> allBoards) {
		allBoards.stream().map(this::buildBoard).forEach(this.boardService::saveOrUpdate);
	}

	private ChanBoard buildBoard(BoardResponse boardResponse) {
		ChanBoard board = new ChanBoard();
		board.setBoard(boardResponse.getBoard());
		board.setDescription(boardResponse.getDescription());
		board.setTitle(boardResponse.getTitle());
		return board;
	}

}