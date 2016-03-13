package com.hugoltsp.chanchan.service;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hugoltsp.chanchan.exception.ImageDownloadException;
import com.hugoltsp.chanchan.exception.ImageWriteException;
import com.hugoltsp.chanchan.utils.Image;

@Service
public class ImageService {

	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

	@Inject
	private ImageWriter writer;

	@Inject
	private ImageDownloader downloader;

	@Async
	public void download(String url) throws ImageDownloadException, MalformedURLException, ExecutionException {
		try {
			Image image = this.downloader.downloadImageFromUrl(url);
			this.writer.writeImage(image);
		} catch (ImageDownloadException e) {
			logger.debug("Could not download board image at the following URL: {}, Error: {}", url, e);
		} catch (MalformedURLException e) {
			logger.debug("Error: ", e);
		} catch (ImageWriteException e) {
			logger.debug("An error ocurred while trying to write the image on disk: {}", e);
		}
	}
}
