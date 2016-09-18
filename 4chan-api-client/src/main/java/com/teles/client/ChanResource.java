package com.teles.client;

import java.util.List;

import com.teles.client.domain.response.CatalogPage;
import com.teles.client.domain.response.Thread;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers(value = "User-Agent: ")
interface ChanResource {

	@RequestLine(value = "GET /{board}/catalog.json")
	List<CatalogPage> getCatalog(@Param(value = "board") String board);

	@RequestLine(value = "GET /{board}/thread/{number}.json")
	Thread getThreadPosts(@Param(value = "board") String board, @Param(value = "number") int number);
	
}