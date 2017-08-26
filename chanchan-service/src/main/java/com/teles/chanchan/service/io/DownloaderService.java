package com.teles.chanchan.service.io;

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

import com.teles.chanchan.domain.settings.ChanchanSettings;

@Service
public class DownloaderService {

	private static final int EOF = -1;

	private static final Logger logger = LoggerFactory.getLogger(DownloaderService.class);

	private static final int BUFFER_SIZE = 16384;

	private final String outputPath;

	public DownloaderService(ChanchanSettings settings) {
		this.outputPath = settings.getIo().getOutputPath();
	}

	public void downloadImage(String path) {

		try {

			logger.info("Downloading image at:: {}", path);

			URL url = new URL(path);
			File file = createFile(url.getPath());

			try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
					InputStream inputStream = url.openStream()) {
				write(outputStream, inputStream);
			}

		} catch (IOException e) {
			logger.error("Could not download file at :: {} ", path, e);
		}
	}

	private static void write(OutputStream outputStream, InputStream inputStream) throws IOException {
		byte[] buf = new byte[BUFFER_SIZE];
		int n;
		while (EOF != (n = inputStream.read(buf))) {
			outputStream.write(buf, 0, n);
		}
	}

	private File createFile(String name) {
		String path = new StringBuilder().append(this.outputPath).append(name).toString();
		File file = new File(path);
		file.getParentFile().mkdirs();
		return file;
	}

}