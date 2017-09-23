package com.teles.chanchan.domain.response;

import java.util.ArrayList;
import java.util.List;

public class ThreadsResponse {

	private int page;

	private List<ThreadResponse> threads = new ArrayList<>();

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

}
