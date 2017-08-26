package com.teles.chanchan.service.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.exception.ChanchanMediaDownloadException;
import com.teles.chanchan.domain.settings.ChanchanSettings;

@Service
public class DownloaderService {

	private static final Logger logger = LoggerFactory.getLogger(DownloaderService.class);

	private static final int BUFFER_SIZE = 16384;

	private final ChanchanSettings settings;

	public DownloaderService(ChanchanSettings settings) {
		this.settings = settings;
	}

	public void downloadImage(String path) throws ChanchanMediaDownloadException {

		try {
			logger.info("Downloading image at:: {}", path);
			URL url = new URL(path);
			byte[] bytes = IOUtils.toByteArray(url);
			this.writeImage(url.getPath(), bytes);
		} catch (IOException | ChanchanMediaDownloadException e) {
			logger.error("Could not download file at :: {} ", path, e);
		}
	}

	private void writeImage(String name, byte[] bytes) throws ChanchanMediaDownloadException {
		OutputStream outputStream = null;

		try {
			String path = this.settings.getIo().getOutputPath() + name;
			File file = new File(path);
			file.getParentFile().mkdirs();

			outputStream = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
			outputStream.write(bytes);
		} catch (IOException e) {
			throw new ChanchanMediaDownloadException(e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}

	}

}