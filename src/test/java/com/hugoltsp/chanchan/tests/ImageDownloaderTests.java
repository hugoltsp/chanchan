package com.hugoltsp.chanchan.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import org.junit.Test;

import com.hugoltsp.chanchan.utils.Image;
import com.hugoltsp.chanchan.utils.ImageDownloader;

public class ImageDownloaderTests {

	@Test
	public void downloadImageFromUrlTest() throws Exception {
		try {

			long currentTimeMillis = System.currentTimeMillis();

			URL url = new URL("http://i.4cdn.org/wg/1457580828675.jpg");
			Image downloadedImage = ImageDownloader.downloadImageFromUrl(url);

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
