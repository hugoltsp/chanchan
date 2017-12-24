package com.teles.chanchan.web.app.scheduler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.document.ChanBoard;
import com.teles.chanchan.domain.document.ChanPost;
import com.teles.chanchan.domain.document.ChanPostContent;
import com.teles.chanchan.domain.document.ChanThread;
import com.teles.chanchan.fourchan.api.client.FourchanClient;
import com.teles.chanchan.fourchan.api.client.dto.response.PostResponse;
import com.teles.chanchan.fourchan.api.client.dto.response.ThreadResponse;
import com.teles.chanchan.web.app.service.BoardService;
import com.teles.chanchan.web.app.service.ThreadService;

@Component
public class ThreadScheduler {

	private static final Logger logger = LoggerFactory.getLogger(ThreadScheduler.class);

	private static final int THIRTY_MINUTES = 1000 * 60 * 30;

	private final BoardService boardService;
	private final ThreadService threadService;
	private final FourchanClient fourchanClient;

	public ThreadScheduler(BoardService boardService, ThreadService threadService, FourchanClient fourchanClient) {
		this.boardService = boardService;
		this.threadService = threadService;
		this.fourchanClient = fourchanClient;
	}

	@Scheduled(fixedDelay = THIRTY_MINUTES)
	public void updateThreads() {
		logger.info("Updating Threads.");
		List<ChanBoard> allBoards = this.boardService.findAll();

		allBoards.parallelStream().map(ChanBoard::getBoard).map(this.fourchanClient::getThreadsFromBoard)
				.flatMap(this::flatMapThreadDocuments).forEach(this::save);

	}

	private void save(ChanThread thread) {
		thread.setPosts(this.fourchanClient.getPostsFromBoardAndThreadNumber(thread.getBoard(), thread.getNumber())
				.stream().map(this::buildPost).collect(Collectors.toList()));
		this.threadService.saveOrUpdate(thread);
	}

	private Stream<ChanThread> flatMapThreadDocuments(List<ThreadResponse> threadResponse) {
		return threadResponse.stream().map(this::buildThread);
	}

	private ChanThread buildThread(ThreadResponse threadResponse) {
		ChanThread thread = new ChanThread();
		thread.setDescription(threadResponse.getDescription());
		thread.setLastModified(new Date(threadResponse.getLastModified()));
		thread.setCreationDate(new Date());
		thread.setName(threadResponse.getName());
		thread.setNumber(threadResponse.getNumber());
		thread.setSemanticUrl(threadResponse.getSemanticUrl());
		thread.setSemanticUrl(threadResponse.getSemanticUrl());
		thread.setBoard(threadResponse.getBoard());
		return thread;
	}

	private ChanPost buildPost(PostResponse response) {

		ChanPost post = new ChanPost();
		post.setComentary(response.getComentary());
		post.setNumber(response.getNumber());

		if (response.getTimeStamp() != null) {
			post.setTimeStamp(new Date(response.getTimeStamp()));
		}

		post.setPostContent(buildPostContent(response));

		return post;
	}

	private ChanPostContent buildPostContent(PostResponse response) {
		ChanPostContent postContent = new ChanPostContent();
		postContent.setContentUrl(response.getContentUrl());
		postContent.setFileExtension(response.getFileExtension());
		postContent.setFileSize(response.getFileSize());
		postContent.setImageHeight(response.getImageHeight());
		postContent.setImageWidth(response.getImageWidth());
		postContent.setMd5(response.getMd5());
		postContent.setOriginalFileName(response.getOriginalFileName());
		postContent.setThumbUrl(response.getThumbUrl());
		return postContent;
	}

}
