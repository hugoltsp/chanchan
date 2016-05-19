package com.hugoltsp.chanchan.service;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hugoltsp.chanchan.exception.ImageDownloadException;
import com.hugoltsp.chanchan.utils.Image;

@Service
public class ImageDownloader {

	private static final Logger logger = LoggerFactory.getLogger(ImageDownloader.class);

	/**
	 * 
	 * @param url
	 *            image source
	 * @return image representation containing it's name and bytes array
	 * @throws ImageDownloadException
	 */
	public Image downloadImage(URL url) throws ImageDownloadException {

		try {
			logger.info("Downloading image at:: {}", url.toString());
			byte[] bytes = IOUtils.toByteArray(url);
			return new Image().withFile(bytes).withName(url.getPath());
		} catch (IOException e) {
			throw new ImageDownloadException(e);
		}
	}

}