package com.hugoltsp.chanchan.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.hugoltsp.chanchan.exception.ChanchanException;
import com.hugoltsp.chanchan.exception.ChanchanInvalidMediaException;
import com.hugoltsp.chanchan.exception.ChanchanMediaDownloadException;
import com.hugoltsp.chanchan.exception.ChanchanMediaWriteException;
import com.hugoltsp.chanchan.spring.config.ChanchanConfig;
import com.hugoltsp.chanchan.utils.Image;

public class MediaService {

	private static final Logger logger = LoggerFactory.getLogger(MediaService.class);

	private final MediaWriter writer;
	private final MediaDownloader downloader;

	@Inject
	public MediaService(ChanchanConfig config) {
		this.downloader = new MediaDownloader();
		this.writer = new MediaWriter(config.getOutputPath());
	}

	@Async
	public void download(String url) throws ChanchanException {
		try {
			Image image = this.downloader.downloadImage(new URL(url));
			this.writer.writeImage(image);
		} catch (ChanchanMediaDownloadException e) {
			logger.error("Could not download board image at the following URL: " + url + ", Error: ", e);
			throw e;
		} catch (ChanchanMediaWriteException e) {
			logger.error("An error ocurred while trying to write the image on disk:", e);
			throw e;
		} catch (ChanchanInvalidMediaException e) {
			logger.debug("Invalid image at: {}", url, e);
			throw e;
		} catch (MalformedURLException e) {
			logger.error("Url formation Error: ", e);
			throw new ChanchanException(e);
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new ChanchanException(e);
		}
	}
}
