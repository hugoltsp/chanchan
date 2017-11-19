package com.teles.chanchan.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.teles.chanchan.data.repository.BoardRepository;
import com.teles.chanchan.domain.constants.GuavaCacheConstants;
import com.teles.chanchan.domain.orm.Board;
import com.teles.chanchan.dto.api.client.response.BoardResponse;

@Cacheable(GuavaCacheConstants.DEFAULT_CACHE)
@Service
public class BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

	private final BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	@Transactional
	public void create(BoardResponse boardResponse) {
		logger.debug("Saving board: {}", boardResponse.getBoard());
		Board board = buildBoardEntity(boardResponse);
		this.boardRepository.save(board);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Board> findAll() {
		return this.boardRepository.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public Board findByBoard(String board) {
		return this.boardRepository.findByBoard(board);
	}

	private static Board buildBoardEntity(BoardResponse boardResponse) {
		Board board = new Board();
		board.setBoard(boardResponse.getBoard());
		board.setDescription(boardResponse.getDescription());
		board.setTitle(boardResponse.getTitle());
		return board;
	}

}