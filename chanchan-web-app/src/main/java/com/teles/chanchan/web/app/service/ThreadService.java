package com.teles.chanchan.web.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.web.app.document.mongo.ChanThread;
import com.teles.chanchan.web.app.repository.ChanThreadRepository;

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
		logger.debug("Searching for thread number '{}' on board '{}'.", number, board);
		return this.repository.findByNumberAndBoard(number, board);
	}

	public List<ChanThread> findByBoard(String board) {
		logger.debug("Searching for threads on board '{}'.", board);
		return this.repository.findByBoard(board);
	}

	public List<ChanThread> findAll() {
		List<ChanThread> allChanThreads = this.repository.findAll();
		logger.debug("Found {} ChanThreads", allChanThreads.size());
		return allChanThreads;
	}
}