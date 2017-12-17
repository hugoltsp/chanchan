package com.teles.chanchan.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.data.repository.ThreadRepository;
import com.teles.chanchan.domain.document.ChanThread;
import com.teles.chanchan.fourchan.api.client.FourchanChanResourceClient;
import com.teles.chanchan.fourchan.api.client.dto.response.ThreadResponse;

@Service
public class ThreadService {

	private static final Logger logger = LoggerFactory.getLogger(ThreadService.class);

	private final ThreadRepository threadRepository;
	private final FourchanChanResourceClient chanResourceClient;

	public ThreadService(ThreadRepository threadRepository, FourchanChanResourceClient chanResourceClient) {
		this.threadRepository = threadRepository;
		this.chanResourceClient = chanResourceClient;
	}

	public void save(ChanThread thread) {
		logger.debug("Saving thread num: {}, board: {}", thread.getNumber(), thread.getBoard());
		this.threadRepository.save(thread);
	}

	public List<ThreadResponse> findThreadsFromBoard(String board) {

		List<ThreadResponse> threadsFromBoard = this.chanResourceClient.getThreadsFromBoard(board);
		logger.info("Total of {} threads found on board {}.", threadsFromBoard.size(), board);

		return threadsFromBoard;
	}

}