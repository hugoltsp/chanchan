package com.teles.chanchan.domain.fourchan;

import java.util.ArrayList;
import java.util.List;

public class FourchanCatalogPage {

	private String board;
	private int page;
	private List<FourchanThread> threads = new ArrayList<>();

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
		this.getThreads().stream().forEach(t -> t.setBoard(board));
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<FourchanThread> getThreads() {
		return threads;
	}

	public void setThreads(List<FourchanThread> threads) {
		this.threads = threads;
	}

	public void addThread(FourchanThread thread) {
		this.threads.add(thread);
	}

	@Override
	public String toString() {
		return "Catalog [board=" + board + ", page=" + page + ", threads=" + threads + "]";
	}

}