package com.teles.chanchan.web.app.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.data.repository.ChanThreadRepository;
import com.teles.chanchan.domain.document.ChanThread;

@Service
public class ThreadService {

	private static final Logger logger = LoggerFactory.getLogger(ThreadService.class);

	private final ChanThreadRepository repository;

	public ThreadService(ChanThreadRepository threadRepository) {
		this.repository = threadRepository;
	}

	public void save(ChanThread thread) {
		logger.debug("Saving thread num: {}, board: {}", thread.getNumber(), thread.getBoard());
		this.repository.save(thread);
	}

	public void saveOrUpdate(ChanThread thread) {
		findByNumberAndBoard(thread.getNumber(), thread.getBoard()).ifPresent(t -> thread.setId(t.getId()));
		save(thread);
	}

	public Optional<ChanThread> findByNumberAndBoard(int number, String board) {
		logger.info("Searching for thread {} found on board {}.", number, board);
		return this.repository.findByNumberAndBoard(number, board);
	}

}