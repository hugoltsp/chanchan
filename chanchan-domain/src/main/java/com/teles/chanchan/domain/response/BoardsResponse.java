package com.teles.chanchan.domain.response;

import java.util.ArrayList;
import java.util.List;

public class BoardsResponse {

	private List<BoardResponse> boards = new ArrayList<>();

	public List<BoardResponse> getBoards() {
		return boards;
	}

	public void setBoards(List<BoardResponse> boards) {
		this.boards = boards;
	}

	@Override
	public String toString() {
		return "Boards [boards=" + boards + "]";
	}

}
