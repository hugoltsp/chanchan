package com.teles.chanchan.web.app.dto;

import com.teles.chanchan.web.app.document.mongo.ChanBoard;

public class BoardResponse {

	private String board;

	private String description;

	private String title;

	public BoardResponse(ChanBoard board) {
		this.board = board.getBoard();
		this.description = board.getDescription();
		this.title = board.getTitle();
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "BoardResponse [board=" + board + ", description=" + description + ", title=" + title + "]";
	}

}
