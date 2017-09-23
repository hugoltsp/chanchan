package com.teles.chanchan.domain.response;

import java.util.ArrayList;
import java.util.List;

public class CatalogPageResponse {

	private int page;
	
	private List<ThreadResponse> threads = new ArrayList<>();

	public void setBoard(String board) {
		this.getThreads().stream().forEach(t -> t.setBoard(board));
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<ThreadResponse> getThreads() {
		return threads;
	}

	public void setThreads(List<ThreadResponse> threads) {
		this.threads = threads;
	}

	public void addThread(ThreadResponse thread) {
		this.threads.add(thread);
	}

	@Override
	public String toString() {
		return "FourchanCatalogPage [page=" + page + ", threads=" + threads + "]";
	}

}