package com.teles.chanchan.fourchan.api.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleThreadResponse {

	@JsonProperty("no")
	private Integer number;

	@JsonProperty("last_modified")
	private Long lastModified;

	private String board;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getLastModified() {
		return lastModified;
	}
	
	public Long getLastModifiedMillis() {
		return lastModified * 1000L;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "SimpleThreadResponse [number=" + number + ", lastModified=" + lastModified + ", board=" + board + "]";
	}
}