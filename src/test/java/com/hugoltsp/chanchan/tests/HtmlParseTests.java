package com.hugoltsp.chanchan.tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class HtmlParseTests {

	@Test
	public void test_catalog_parse() throws Exception {

		URL url = new URL("http://boards.4chan.org/wg/catalog");
		URLConnection openConnection = url.openConnection();
		openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
	
		InputStreamReader input = new InputStreamReader(openConnection.getInputStream());
	    BufferedReader in = new BufferedReader(input);
		
	    String inputLine;
	    while ((inputLine = in.readLine()) != null) {
	        System.out.println(inputLine+"\n");
	    }
		
//		Connection connect = Jsoup.connect("http://boards.4chan.org/wg/catalog");
//		connect.userAgent("");
//		connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		Document document = connect.get();
//	
//		System.out.println(document);
	}

}
