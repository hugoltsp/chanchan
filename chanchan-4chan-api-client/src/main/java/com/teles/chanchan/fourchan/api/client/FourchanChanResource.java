package com.teles.chanchan.fourchan.api.client;

import java.util.List;

import com.teles.chanchan.domain.response.BoardsResponse;
import com.teles.chanchan.domain.response.CatalogResponse;
import com.teles.chanchan.domain.response.ThreadResponse;
import com.teles.chanchan.domain.response.ThreadsResponse;

import feign.Param;
import feign.RequestLine;

interface FourchanChanResource {

	@RequestLine(value = "GET /{board}/catalog.json")
	List<CatalogResponse> getCatalog(@Param(value = "board") String board);

	@RequestLine(value = "GET /{board}/thread/{number}.json")
	ThreadResponse getPosts(@Param(value = "board") String board, @Param(value = "number") int number);
	
	@RequestLine(value = "GET /boards.json")
	BoardsResponse geAllBoards();

	@RequestLine(value = "GET /{board}/threads.json")
	List<ThreadsResponse> getThreads(@Param(value = "board") String board);
	
}