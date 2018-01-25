package com.teles.chanchan.web.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.document.ChanBoard;
import com.teles.chanchan.web.app.repository.BoardRepository;

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
		findByBoard(board.getBoard()).ifPresent(b -> board.setId(b.getId()));
		save(board);
	}

	public List<ChanBoard> findAll() {
		List<ChanBoard> allBoards = this.boardRepository.findAll();
		logger.debug("{} boards.", allBoards.size());
		return allBoards;
	}

	public Optional<ChanBoard> findByBoard(String board) {
		logger.debug("Looking for board '{}'", board);
		return this.boardRepository.findByBoard(board);
	}

}