package com.teles.chanchan.fourchan.api.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoardResponse {

	private String board;

	@JsonProperty("meta_description")
	private String description;

	private String title;

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

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "Board [board=" + board + "]";
	}
}
