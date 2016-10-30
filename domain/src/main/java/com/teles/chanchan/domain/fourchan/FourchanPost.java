package com.teles.chanchan.domain.fourchan;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "4chan-post")
public class FourchanPost {

	@Id
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
	private Integer fileSize;

	@JsonProperty("com")
	private String comentary;

	private String board;

	private String thumbUrl;

	private String contentUrl;

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

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getComentary() {
		return comentary;
	}

	public void setComentary(String comentary) {
		this.comentary = comentary;
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

	@Override
	public String toString() {
		return "FourchanPost [number=" + number + ", originalFileName=" + originalFileName + ", fileExtension="
				+ fileExtension + ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight + ", timeStamp="
				+ timeStamp + ", fileSize=" + fileSize + ", comentary=" + comentary + ", board=" + board + ", thumbUrl="
				+ thumbUrl + ", contentUrl=" + contentUrl + "]";
	}

}
