package com.teles.chanchan.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.orm.Board;
import com.teles.chanchan.dto.api.client.response.ThreadResponse;
import com.teles.chanchan.fourchan.api.client.FourchanChanResourceClient;
import com.teles.chanchan.service.BoardService;
import com.teles.chanchan.service.ThreadService;

@Component
public class ThreadScheduler {

	private static final Logger logger = LoggerFactory.getLogger(ThreadScheduler.class);

	private static final int THIRTY_MINUTES = 1000 * 60 * 30;

	private final BoardService boardService;
	private final FourchanChanResourceClient fourchanChanResourceClient;
	private final ThreadService threadService;

	public ThreadScheduler(BoardService boardService, ThreadService threadService,
			FourchanChanResourceClient fourchanChanResourceClient) {
		this.boardService = boardService;
		this.threadService = threadService;
		this.fourchanChanResourceClient = fourchanChanResourceClient;
	}

	@Scheduled(fixedDelay = THIRTY_MINUTES)
	public void updateThreads() {
		logger.info("Saving Threads.");
		List<Board> allBoards = this.boardService.findAll();
		for (Board board : allBoards) {
			List<ThreadResponse> threadsFromBoard = this.fourchanChanResourceClient
					.getThreadsFromBoard(board.getBoard());
			saveThreadsIfNotFound(threadsFromBoard);
		}
	}

	private void saveThreadsIfNotFound(List<ThreadResponse> threadsFromBoard) {
		for (ThreadResponse threadResponse : threadsFromBoard) {
			this.threadService.create(threadResponse);
		}
	}
}
