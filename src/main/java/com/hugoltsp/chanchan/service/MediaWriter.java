package com.hugoltsp.chanchan.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hugoltsp.chanchan.exception.ChanchanMediaWriteException;
import com.hugoltsp.chanchan.utils.Image;

public class MediaWriter {

	private static final Logger logger = LoggerFactory.getLogger(MediaWriter.class);
	private static final int BUFFER_SIZE = 16384;

	private final String outputPath;

	public MediaWriter(String outputPath) {
		this.outputPath = outputPath;
	}

	public void writeImage(Image image) throws ChanchanMediaWriteException {
		OutputStream outputStream = null;

		try {
			String path = this.outputPath + image.getName();
			logger.info("Writing image to:: {}", path);

			File file = new File(path);
			file.getParentFile().mkdirs();
			file.createNewFile();

			outputStream = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
			outputStream.write(image.getFile());
			outputStream.flush();
		} catch (IOException e) {
			throw new ChanchanMediaWriteException(e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}

	}

}