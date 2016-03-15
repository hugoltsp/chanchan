package com.hugoltsp.chanchan.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hugoltsp.chanchan.exception.ImageDownloadException;
import com.hugoltsp.chanchan.exception.ImageWriteException;
import com.hugoltsp.chanchan.exception.InvalidImageException;
import com.hugoltsp.chanchan.utils.Image;

@Service
public class ImageService {

	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

	@Inject
	private ImageWriter writer;

	@Inject
	private ImageDownloader downloader;

	@Async
	public void download(String url) throws ExecutionException {
		try {
			Image image = this.downloader.downloadImage(new URL(url));
			this.writer.writeImage(image);
		} catch (ImageDownloadException e) {
			logger.error("Could not download board image at the following URL: " + url + ", Error: ", e);
		} catch (MalformedURLException e) {
			logger.error("Url formation Error: ", e);
		} catch (ImageWriteException e) {
			logger.error("An error ocurred while trying to write the image on disk:", e);
		} catch (InvalidImageException e) {
			logger.debug("Invalid image at: {}", url, e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}
}
