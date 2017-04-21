package com.teles.chanchan.fourchan.client;

import java.util.List;

import com.teles.chanchan.domain.client.fourchan.FourchanCatalogPage;
import com.teles.chanchan.domain.client.fourchan.FourchanThread;

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