package com.teles.chanchan.scraper.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.config.settings.IoSettings;

@Service
public class DownloaderService {

	private static final Logger logger = LoggerFactory.getLogger(DownloaderService.class);

	private static final int EOF = -1;
	private static final int BUFFER_SIZE = 16384;

	private final String outputPath;

	public DownloaderService(IoSettings settings) {
		this.outputPath = settings.getOutputPath();
	}

	public void downloadImage(String contentUrl) {

		try {

			logger.info("Downloading image at: {}", contentUrl);

			URL url = new URL(contentUrl);
			try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(createFile(url.getPath())),
					BUFFER_SIZE); InputStream inputStream = url.openStream()) {
				write(outputStream, inputStream);
			}

		} catch (IOException e) {
			logger.error("Could not download file at: {} ", contentUrl, e);
		}
	}

	private static void write(OutputStream outputStream, InputStream inputStream) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int read;
		while (EOF != (read = inputStream.read(buffer))) {
			outputStream.write(buffer, 0, read);
		}
	}

	private File createFile(String name) {
		File file = new File(new StringBuilder().append(this.outputPath).append(name).toString());
		file.getParentFile().mkdirs();
		return file;
	}

}