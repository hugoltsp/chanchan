package com.teles.chanchan.web.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.data.repository.BoardRepository;
import com.teles.chanchan.domain.document.ChanBoard;

@Service
public class BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

	private final BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public void save(ChanBoard board) {
		logger.debug("Saving board: {}", board.getBoard());
		this.boardRepository.save(board);
	}

	public void saveOrUpdate(ChanBoard board) {
		findByBoard(board.getBoard()).ifPresent(b->board.setId(b.getId()));
		save(board);
	}

	public List<ChanBoard> findAll() {
		return this.boardRepository.findAll();
	}

	public Optional<ChanBoard> findByBoard(String board) {
		return this.boardRepository.findByBoard(board);
	}

}