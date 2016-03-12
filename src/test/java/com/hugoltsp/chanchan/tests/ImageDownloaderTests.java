package com.hugoltsp.chanchan.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hugoltsp.chanchan.service.ImageDownloader;
import com.hugoltsp.chanchan.utils.Image;

@RunWith(SpringJUnit4ClassRunner.class)
public class ImageDownloaderTests {

	@Inject
	private ImageDownloader downloader;

	@Test
	public void downloadImageFromUrlTest() throws Exception {
		try {

			long currentTimeMillis = System.currentTimeMillis();

			URL url = new URL("http://i.4cdn.org/wg/1457580828675.jpg");
			Image downloadedImage = downloader.downloadImageFromUrl(url);

			File file = new File("/home/hugo/chanchan/out" + downloadedImage.getName());
			file.getParentFile().mkdir();
			file.createNewFile();

			OutputStream outputStream = new FileOutputStream(file);
			outputStream.write(downloadedImage.getFile());
			outputStream.close();

			long time = ((System.currentTimeMillis() - currentTimeMillis));

			System.out.println(downloadedImage.getName() + " :: " + time);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
