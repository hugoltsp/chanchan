package com.teles.chanchan.web.app.endpoint;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teles.chanchan.web.app.document.mongo.ChanBoard;
import com.teles.chanchan.web.app.document.mongo.ChanThread;
import com.teles.chanchan.web.app.dto.BoardResponse;
import com.teles.chanchan.web.app.dto.ThreadResponse;
import com.teles.chanchan.web.app.service.BoardService;
import com.teles.chanchan.web.app.service.ThreadService;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/board")
public class BoardEndpoint {

	private final BoardService boardService;
	private final ThreadService threadService;

	public BoardEndpoint(BoardService boardService, ThreadService threadService) {
		this.boardService = boardService;
		this.threadService = threadService;
	}

	@GetMapping
	public ResponseEntity<?> getBoards() {
		ResponseEntity<?> responseEntity = null;

		List<BoardResponse> boards = boardService.findAll().stream().map(BoardResponse::new)
				.collect(Collectors.toList());

		if (boards.isEmpty()) {
			responseEntity = ResponseEntity.noContent().build();
		} else {
			responseEntity = ResponseEntity.ok(boards);
		}

		return responseEntity;
	}

	@GetMapping("/{board}")
	public ResponseEntity<?> getBoard(@PathVariable String board) {

		ResponseEntity<?> responseEntity = null;

		Optional<ChanBoard> boardOptional = this.boardService.findByBoard(board.toLowerCase());

		if (boardOptional.isPresent()) {
			responseEntity = ResponseEntity.ok(boardOptional.map(BoardResponse::new).get());
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}

		return responseEntity;
	}

	@GetMapping("/{board}/thread")
	public ResponseEntity<?> getThreadsFromBoard(@PathVariable String board) {

		ResponseEntity<?> responseEntity = null;

		List<ChanThread> threads = this.threadService.findByBoard(board.toLowerCase());

		if (threads.isEmpty()) {
			responseEntity = ResponseEntity.noContent().build();
		} else {
			responseEntity = ResponseEntity.ok(threads.stream().map(ThreadResponse::new).collect(Collectors.toList()));
		}

		return responseEntity;
	}

}
