package com.teles.chanchan.domain.response;

import java.util.List;

public class ThreadsResponse {

	private int page;

	private List<SimpleThreadResponse> threads;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<SimpleThreadResponse> getThreads() {
		return threads;
	}

	public void setThreads(List<SimpleThreadResponse> threads) {
		this.threads = threads;
	}

}