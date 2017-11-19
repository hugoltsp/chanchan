package com.teles.chanchan.web.app.scheduler;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.document.Board;
import com.teles.chanchan.domain.document.Thread;
import com.teles.chanchan.dto.api.client.response.ThreadResponse;
import com.teles.chanchan.service.BoardService;
import com.teles.chanchan.service.PostService;
import com.teles.chanchan.service.ThreadService;

@Component
public class ThreadScheduler {

	private static final Logger logger = LoggerFactory.getLogger(ThreadScheduler.class);

	private static final int THIRTY_MINUTES = 1000 * 60 * 30;

	private final BoardService boardService;
	private final ThreadService threadService;
	private final PostService postService;

	public ThreadScheduler(BoardService boardService, ThreadService threadService, PostService postService) {
		this.boardService = boardService;
		this.threadService = threadService;
		this.postService = postService;
	}

	@Scheduled(fixedDelay = THIRTY_MINUTES)
	public void updateThreads() {
		logger.info("Updating Threads.");
		List<Board> allBoards = this.boardService.findAll();

		allBoards.parallelStream().map(Board::getBoard).map(this.threadService::findThreadsFromBoard)
				.flatMap(this::flatMapThreadDocuments).forEach(this::save);

	}

	private void save(Thread thread) {
		thread.setPosts(this.postService.findPosts(thread.getBoard(), thread.getNumber()));
		this.threadService.save(thread);
	}

	private Stream<Thread> flatMapThreadDocuments(List<ThreadResponse> threadResponse) {
		return threadResponse.stream().map(this::buildThreadDocument);
	}

	private Thread buildThreadDocument(ThreadResponse threadResponse) {
		Thread thread = new Thread();
		thread.setDescription(threadResponse.getDescription());
		thread.setLastModified(new Date(threadResponse.getLastModified()));
		thread.setName(threadResponse.getName());
		thread.setNumber(threadResponse.getNumber());
		thread.setSemanticUrl(threadResponse.getSemanticUrl());
		thread.setSemanticUrl(threadResponse.getSemanticUrl());
		thread.setBoard(threadResponse.getBoard());
		return thread;
	}

}
