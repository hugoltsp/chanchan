package com.teles.chanchan.service;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.teles.chanchan.exception.ChanchanMediaDownloadException;
import com.teles.chanchan.utils.Image;

public class MediaDownloader {

	private static final Logger logger = LoggerFactory.getLogger(MediaDownloader.class);

	/**
	 * 
	 * @param url
	 *            image source
	 * @return image representation containing it's name and bytes array
	 * @throws ChanchanMediaDownloadException
	 */
	public Image downloadImage(URL url) throws ChanchanMediaDownloadException {

		try {
			logger.info("Downloading image at:: {}", url.toString());
			byte[] bytes = IOUtils.toByteArray(url);
			return new Image().withFile(bytes).withName(url.getPath());
		} catch (IOException e) {
			throw new ChanchanMediaDownloadException(e);
		}
	}

}