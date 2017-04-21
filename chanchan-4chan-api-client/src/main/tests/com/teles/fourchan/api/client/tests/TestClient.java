package com.teles.fourchan.api.client.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.teles.fourchan.api.client.FourChanFeignClient;
import com.teles.fourchan.api.client.domain.response.CatalogPage;

public class TestClient {

	@Test
	public void test_get_catalog() throws Exception {
		FourChanFeignClient fourChanFeignClient = new FourChanFeignClient();
		List<CatalogPage> catalog = fourChanFeignClient.getCatalogPages("wg");
		System.out.println(catalog);
		assertNotNull(catalog);
	}
}
