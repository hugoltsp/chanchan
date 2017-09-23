package com.teles.chanchan.fourchan.api.client;

import java.util.List;

import com.teles.chanchan.domain.response.BoardsResponse;
import com.teles.chanchan.domain.response.CatalogPageResponse;
import com.teles.chanchan.domain.response.ThreadResponse;
import com.teles.chanchan.domain.response.ThreadsResponse;

import feign.Param;
import feign.RequestLine;

interface FourchanChanResource {

	@RequestLine(value = "GET /{board}/catalog.json")
	List<CatalogPageResponse> getCatalog(@Param(value = "board") String board);

	@RequestLine(value = "GET /{board}/thread/{number}.json")
	ThreadResponse getThreadPosts(@Param(value = "board") String board, @Param(value = "number") int number);
	
	@RequestLine(value = "GET /boards.json")
	BoardsResponse geAllBoards();

	@RequestLine(value = "GET /{board}/threads.json")
	ThreadsResponse getThreads(@Param(value = "board") String board);
	
}