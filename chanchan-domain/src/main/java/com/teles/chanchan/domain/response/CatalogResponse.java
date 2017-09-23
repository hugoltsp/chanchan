package com.teles.chanchan.domain.response;

import java.util.List;

public class CatalogResponse {

	private int page;

	private List<ThreadResponse> threads;

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

	@Override
	public String toString() {
		return "CatalogResponse [page=" + page + ", threads=" + threads + "]";
	}

}