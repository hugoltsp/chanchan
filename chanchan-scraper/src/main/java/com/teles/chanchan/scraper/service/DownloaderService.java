package com.teles.chanchan.scraper.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.scraper.api.client.response.PostResponse;
import com.teles.chanchan.scraper.settings.IoSettings;

@Service
public class DownloaderService {

	private static final String SLASH = "/";

	private static final Logger logger = LoggerFactory.getLogger(DownloaderService.class);

	private static final int EOF = -1;
	private static final int BUFFER_SIZE = 16384;

	private final String outputPath;

	public DownloaderService(IoSettings settings) {
		this.outputPath = settings.getOutputPath();
	}

	public void downloadImage(PostResponse postResponse) {

		try {

			logger.info("Downloading content from: {}", postResponse.getContentUrl());

			try (OutputStream outputStream = new BufferedOutputStream(createFile(postResponse), BUFFER_SIZE);
					InputStream inputStream = new URL(postResponse.getContentUrl()).openStream()) {
				write(outputStream, inputStream);
			}

		} catch (IOException e) {
			logger.error("Could not download file at: {} ", postResponse.getContentUrl(), e);
		}

	}

	private FileOutputStream createFile(PostResponse postResponse) throws FileNotFoundException {
		File file = new File(new StringBuilder()
						.append(this.outputPath)
						.append(SLASH)
						.append(createFileName(postResponse))
						.toString());
		file.getParentFile().mkdirs();
		return new FileOutputStream(file);
	}

	private static void write(OutputStream outputStream, InputStream inputStream) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int read;
		while (EOF != (read = inputStream.read(buffer))) {
			outputStream.write(buffer, 0, read);
		}
	}

	private static String createFileName(PostResponse post) {
		return new StringBuilder()
				.append(post.getBoard()).append(SLASH)
				.append(post.getThread()).append(SLASH)
				.append(post.getTimeStamp()).append(post.getFileExtension()).toString();
	}

}