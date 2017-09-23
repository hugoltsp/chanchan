package com.teles.chanchan.fourchan.api.client;

import java.util.List;

import com.teles.chanchan.dto.api.client.response.BoardResponse;
import com.teles.chanchan.dto.api.client.response.PostResponse;
import com.teles.chanchan.dto.api.client.response.SimpleThreadResponse;
import com.teles.chanchan.dto.api.client.response.ThreadResponse;

import feign.Param;
import feign.RequestLine;

interface FourchanChanResource {

	@RequestLine(value = "GET /{board}/catalog.json")
	List<CatalogResponse> getCatalog(@Param(value = "board") String board);

	@RequestLine(value = "GET /{board}/thread/{number}.json")
	PostsResponse getPosts(@Param(value = "board") String board, @Param(value = "number") int number);

	@RequestLine(value = "GET /boards.json")
	BoardsResponse geAllBoards();

	@RequestLine(value = "GET /{board}/threads.json")
	List<ThreadsResponse> getThreads(@Param(value = "board") String board);

	class BoardsResponse {

		private List<BoardResponse> boards;

		public List<BoardResponse> getBoards() {
			return boards;
		}

		public void setBoards(List<BoardResponse> boards) {
			this.boards = boards;
		}

	}

	class CatalogResponse {

		private List<ThreadResponse> threads;

		public List<ThreadResponse> getThreads() {
			return threads;
		}

		public void setThreads(List<ThreadResponse> threads) {
			this.threads = threads;
		}
	}

	class PostsResponse {

		private List<PostResponse> posts;

		public List<PostResponse> getPosts() {
			return posts;
		}

		public void setPosts(List<PostResponse> posts) {
			this.posts = posts;
		}
	}

	class ThreadsResponse {

		private List<SimpleThreadResponse> threads;

		public List<SimpleThreadResponse> getThreads() {
			return threads;
		}

		public void setThreads(List<SimpleThreadResponse> threads) {
			this.threads = threads;
		}

	}
}