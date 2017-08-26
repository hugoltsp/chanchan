package com.teles.chanchan.domain;

import java.util.ArrayList;
import java.util.List;

public class FourchanCatalogPage {

	private int page;
	private List<FourchanThread> threads = new ArrayList<>();

	public void setBoard(String board) {
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
		return "FourchanCatalogPage [page=" + page + ", threads=" + threads + "]";
	}

}