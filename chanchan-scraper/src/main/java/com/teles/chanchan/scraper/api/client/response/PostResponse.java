package com.teles.chanchan.scraper.api.client.response;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostResponse {

	@JsonProperty("no")
	private Integer number;

	@JsonProperty("filename")
	private String originalFileName;

	@JsonProperty("ext")
	private String fileExtension;

	@JsonProperty("w")
	private Integer imageWidth;

	@JsonProperty("h")
	private Integer imageHeight;

	@JsonProperty("tim")
	private Long timeStamp;

	@JsonProperty("fsize")
	private Long fileSize;

	@JsonProperty("com")
	private String comentary;

	private String board;

	private Integer thread;

	private String thumbUrl;

	private String contentUrl;

	private String md5;

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getComentary() {
		return comentary;
	}

	public void setComentary(String comentary) {
		this.comentary = unescapeHtml4(comentary);
	}

	public Integer getThread() {
		return thread;
	}

	public void setThread(Integer thread) {
		this.thread = thread;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public boolean hasMedia() {
		return this.fileExtension != null && this.timeStamp != 0;
	}

	@Override
	public String toString() {
		return "PostResponse [number=" + number + ", originalFileName=" + originalFileName + ", fileExtension="
				+ fileExtension + ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight + ", timeStamp="
				+ timeStamp + ", fileSize=" + fileSize + ", comentary=" + comentary + ", board=" + board + ", thread="
				+ thread + ", thumbUrl=" + thumbUrl + ", contentUrl=" + contentUrl + ", md5=" + md5 + "]";
	}

}
