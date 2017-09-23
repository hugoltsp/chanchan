package com.teles.chanchan.fourchan.api.client.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.domain.settings.ChanchanSettings.ClientFourChan;
import com.teles.chanchan.fourchan.api.client.FourchanChanResourceClient;
import com.teles.chanchan.fourchan.api.client.content.ContentUrlResolver;

public class FourchanClientTests {

	private static final String BOARD_WALLPAPER_GENERAL = "wg";
	private static final String CDN_4CHAN = "http://i.4cdn.org";
	private static final String MINIATURE_SUFFIX = "s.jpg";
	private static final String API_4CHAN = "http://api.4chan.org";

	private FourchanChanResourceClient fourchanChanResourceClient;

	@Before
	public void onStart() {
		ChanchanSettings chanchanSettings = chanChanSettings();
		ContentUrlResolver imageUrlResolver = new ContentUrlResolver(chanchanSettings);
		this.fourchanChanResourceClient = new FourchanChanResourceClient(imageUrlResolver, chanchanSettings);
	}

	@Test
	public void test_get_boards() throws Exception {

		try {

			this.fourchanChanResourceClient.getAllBoards().forEach(System.out::println);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void test_get_catalog_from_board() throws Exception {

		try {

			this.fourchanChanResourceClient.getThreadsFromBoard(BOARD_WALLPAPER_GENERAL).forEach(System.out::println);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void test_get_posts_from_thread() throws Exception {

		try {

			this.fourchanChanResourceClient
					.getPostsFromBoardAndThreadNumber(BOARD_WALLPAPER_GENERAL, this.fourchanChanResourceClient
							.getSimpleThreadsFromBoard(BOARD_WALLPAPER_GENERAL).stream().findFirst().get().getNumber())
					.forEach(System.out::println);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void test_get_simple_threads_from_board() throws Exception {

		try {

			this.fourchanChanResourceClient.getSimpleThreadsFromBoard(BOARD_WALLPAPER_GENERAL)
					.forEach(System.out::println);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	private static ChanchanSettings chanChanSettings() {
		ChanchanSettings chanchanSettings = new ChanchanSettings();
		ClientFourChan clientFourChan = chanchanSettings.getClientFourChan();
		clientFourChan.setApiUrl(API_4CHAN);
		clientFourChan.setMiniatureSuffix(MINIATURE_SUFFIX);
		clientFourChan.setCdnUrl(CDN_4CHAN);
		return chanchanSettings;
	}

}
