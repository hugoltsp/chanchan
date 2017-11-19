package com.teles.chanchan.service;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teles.chanchan.data.repository.BoardRepository;
import com.teles.chanchan.data.repository.ThreadRepository;
import com.teles.chanchan.domain.constants.GuavaCacheConstants;
import com.teles.chanchan.domain.orm.Board;
import com.teles.chanchan.domain.orm.Thread;
import com.teles.chanchan.dto.api.client.response.ThreadResponse;

@Cacheable(GuavaCacheConstants.DEFAULT_CACHE)

@Service
public class ThreadService {

	private static final Logger logger = LoggerFactory.getLogger(ThreadService.class);

	private final ThreadRepository threadRepository;
	private final BoardRepository boardRepository;

	public ThreadService(ThreadRepository threadRepository, BoardRepository boardRepository) {
		this.threadRepository = threadRepository;
		this.boardRepository = boardRepository;
	}

	@Transactional
	public void create(ThreadResponse threadResponse) {
		logger.debug("Saving thread: {}", threadResponse);
		Thread thread = buildThreadEntity(threadResponse);
		this.threadRepository.save(thread);
	}

	private Thread buildThreadEntity(ThreadResponse threadResponse) {
		Thread thread = new Thread();
		Board board = this.boardRepository.findByBoard(threadResponse.getBoard());
		thread.setDescription(threadResponse.getDescription());
		thread.setLastModified(Date.from(Instant.ofEpochMilli(threadResponse.getLastModified())));
		thread.setName(threadResponse.getName());
		thread.setNumber(threadResponse.getNumber());
		thread.setSemanticUrl(threadResponse.getSemanticUrl());
		thread.setSemanticUrl(threadResponse.getSemanticUrl());
		thread.setBoard(board);
		return thread;
	}

}