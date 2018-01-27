package com.teles.chanchan.web.app.scheduler.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.teles.chanchan.fourchan.api.client.FourchanClient;
import com.teles.chanchan.fourchan.api.client.dto.response.PostResponse;
import com.teles.chanchan.fourchan.api.client.dto.response.ThreadResponse;
import com.teles.chanchan.web.app.document.mongo.ChanPost;
import com.teles.chanchan.web.app.document.mongo.ChanPostContent;
import com.teles.chanchan.web.app.document.mongo.ChanThread;
import com.teles.chanchan.web.app.service.ThreadService;

@Component
public class ThreadAsyncHandler {

	private static final Logger logger = LoggerFactory.getLogger(ThreadAsyncHandler.class);

	private final ThreadService threadService;
	private final FourchanClient fourchanClient;

	public ThreadAsyncHandler(ThreadService threadService, FourchanClient fourchanClient) {
		this.threadService = threadService;
		this.fourchanClient = fourchanClient;
	}

	@Async
	public void updateThreadsByBoard(String board) {
		logger.debug("Updating threads of board '{}'", board);

		List<ThreadResponse> threadsFromBoard = this.fourchanClient.getThreadsFromBoard(board);

		threadsFromBoard.stream().map(this::buildThread).forEach(chanThread -> {
			chanThread.setPosts(getPosts(chanThread));
			this.threadService.saveOrUpdate(chanThread);
		});

	}

	private List<ChanPost> getPosts(ChanThread chanThread) {
		return this.fourchanClient.getPostsFromBoardAndThreadNumber(chanThread.getBoard(), chanThread.getNumber())
				.stream().map(this::buildPost).collect(Collectors.toList());
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
