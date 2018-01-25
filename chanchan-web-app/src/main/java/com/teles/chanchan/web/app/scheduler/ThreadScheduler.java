package com.teles.chanchan.web.app.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.document.ChanBoard;
import com.teles.chanchan.web.app.scheduler.handler.ThreadAsyncHandler;
import com.teles.chanchan.web.app.service.BoardService;

@Component
public class ThreadScheduler {

	private static final Logger logger = LoggerFactory.getLogger(ThreadScheduler.class);

	private static final int THIRTY_MINUTES = 1000 * 60 * 30;

	private final BoardService boardService;
	private final ThreadAsyncHandler asyncHandler;

	public ThreadScheduler(BoardService boardService, ThreadAsyncHandler asyncHandler) {
		this.boardService = boardService;
		this.asyncHandler = asyncHandler;
	}

	@Scheduled(fixedDelay = THIRTY_MINUTES)
	public void updateThreads() {
		logger.info("Updating Threads.");

		for (ChanBoard chanBoard : this.boardService.findAll()) {
			this.asyncHandler.updateThreadsByBoard(chanBoard.getBoard());
		}
		
	}

}
