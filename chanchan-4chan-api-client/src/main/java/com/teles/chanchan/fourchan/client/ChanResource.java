package com.teles.chanchan.fourchan.client;

import java.util.List;

import com.teles.chanchan.domain.FourchanCatalogPage;
import com.teles.chanchan.domain.FourchanThread;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers(value = "User-Agent: ")
interface ChanResource {

	@RequestLine(value = "GET /{board}/catalog.json")
	List<FourchanCatalogPage> getCatalog(@Param(value = "board") String board);

	@RequestLine(value = "GET /{board}/thread/{number}.json")
	FourchanThread getThreadPosts(@Param(value = "board") String board, @Param(value = "number") int number);
	
}