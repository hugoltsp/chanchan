package com.teles.client.domain.response;

import java.util.ArrayList;
import java.util.List;

public class CatalogPage {

	private String board;
	private int page;
	private List<Thread> threads = new ArrayList<>();

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

	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public void addThread(Thread thread) {
		this.threads.add(thread);
	}

	@Override
	public String toString() {
		return "Catalog [board=" + board + ", page=" + page + ", threads=" + threads + "]";
	}

}