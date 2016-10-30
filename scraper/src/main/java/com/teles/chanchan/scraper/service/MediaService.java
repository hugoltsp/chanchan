package com.teles.chanchan.scraper.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.teles.chanchan.scraper.config.ChanchanConfig;
import com.teles.chanchan.scraper.exception.ChanchanException;
import com.teles.chanchan.scraper.exception.ChanchanInvalidMediaException;
import com.teles.chanchan.scraper.exception.ChanchanMediaDownloadException;
import com.teles.chanchan.scraper.exception.ChanchanMediaWriteException;
import com.teles.chanchan.scraper.utils.Image;

@Service
public class MediaService {

	private static final Logger logger = LoggerFactory.getLogger(MediaService.class);

	private final MediaWriter writer;
	private final MediaDownloader downloader;

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
