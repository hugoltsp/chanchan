package com.teles.chanchan.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.data.repository.BoardRepository;
import com.teles.chanchan.domain.document.ChanBoard;
import com.teles.chanchan.fourchan.api.client.dto.response.BoardResponse;

@Service
public class BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

	private final BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public void create(BoardResponse boardResponse) {
		logger.debug("Saving board: {}", boardResponse.getBoard());
		ChanBoard board = buildBoardEntity(boardResponse);
		this.boardRepository.save(board);
	}

	public List<ChanBoard> findAll() {
		return this.boardRepository.findAll();
	}

	public ChanBoard findByBoard(String board) {
		return this.boardRepository.findByBoard(board);
	}

	private static ChanBoard buildBoardEntity(BoardResponse boardResponse) {
		ChanBoard board = new ChanBoard();
		board.setBoard(boardResponse.getBoard());
		board.setDescription(boardResponse.getDescription());
		board.setTitle(boardResponse.getTitle());
		return board;
	}

}