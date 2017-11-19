package com.teles.chanchan.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.data.repository.ThreadRepository;
import com.teles.chanchan.domain.document.Thread;
import com.teles.chanchan.dto.api.client.response.ThreadResponse;
import com.teles.chanchan.fourchan.api.client.FourchanChanResourceClient;

@Service
public class ThreadService {

	private static final Logger logger = LoggerFactory.getLogger(ThreadService.class);

	private final ThreadRepository threadRepository;
	private final FourchanChanResourceClient chanResourceClient;

	public ThreadService(ThreadRepository threadRepository, FourchanChanResourceClient chanResourceClient) {
		this.threadRepository = threadRepository;
		this.chanResourceClient = chanResourceClient;
	}

	public void save(Thread thread) {
		logger.debug("Saving thread num: {}, board: {}", thread.getNumber(), thread.getBoard());
		this.threadRepository.save(thread);
	}

	public List<ThreadResponse> findThreadsFromBoard(String board) {

		List<ThreadResponse> threadsFromBoard = this.chanResourceClient.getThreadsFromBoard(board);
		logger.info("Total of {} threads found on board {}.", threadsFromBoard.size(), board);

		return threadsFromBoard;
	}

}