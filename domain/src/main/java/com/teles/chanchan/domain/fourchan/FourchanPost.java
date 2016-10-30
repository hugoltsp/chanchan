package com.teles.chanchan.domain.fourchan;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FourchanPost {

	@JsonProperty("filename")
	private String originalFileName;

	@JsonProperty("ext")
	private String fileExtension;

	@JsonProperty("w")
	private int imageWidth;

	@JsonProperty("h")
	private int imageHeight;

	@JsonProperty("tim")
	private long timeStamp;

	@JsonProperty("fsize")
	private int fileSize;

	private String board;

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String fileName) {
		this.originalFileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "Post [originalFileName=" + originalFileName + ", fileExtension=" + fileExtension + ", imageWidth="
				+ imageWidth + ", imageHeight=" + imageHeight + ", timeStamp=" + timeStamp + ", fileSize=" + fileSize
				+ ", board=" + board + "]";
	}

}
