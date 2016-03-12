package com.hugoltsp.chanchan.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader {

	private static final int BUFFER_SIZE = 16384;

	private ImageDownloader() {

	}

	public static Image downloadImageFromUrl(URL url) throws IOException {
		// TODO
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		Image image = null;

		try {

			inputStream = new BufferedInputStream(url.openStream());
			outputStream = new ByteArrayOutputStream(BUFFER_SIZE);
			byte[] buf = new byte[BUFFER_SIZE];

			int read;
			while ((read = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, read);
			}

			image = new Image().withFile(outputStream.toByteArray()).withName(url.getPath());

		} finally {
			closeQuietly(outputStream, inputStream);
		}

		return image;
	}

	private static void closeQuietly(Closeable... closeables) {
		for (Closeable closeable : closeables) {
			if (null != closeable) {
				try {
					closeable.close();
				} catch (IOException e) {

				}
			}
		}
	}
}