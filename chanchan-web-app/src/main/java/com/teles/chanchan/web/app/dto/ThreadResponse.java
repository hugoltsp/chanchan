package com.teles.chanchan.web.app.dto;

import com.teles.chanchan.web.app.document.mongo.ChanThread;

public class ThreadResponse {

	private Integer number;

	private String board;

	private String name;

	private String description;

	public ThreadResponse(ChanThread chanThread) {
		number = chanThread.getNumber();
		board = chanThread.getBoard();
		name = chanThread.getName();
		description = chanThread.getDescription();
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ThreadResponse [number=" + number + ", board=" + board + ", name=" + name + ", description="
				+ description + "]";
	}

}
