package com.teles.chanchan.web.app.document.mongo;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

public class ChanPost {

	@Indexed
	private Integer number;

	private Date timeStamp;

	private String comentary;

	private ChanPostContent content;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getComentary() {
		return comentary;
	}

	public void setComentary(String comentary) {
		this.comentary = comentary;
	}

	public ChanPostContent getContent() {
		return content;
	}

	public void setContent(ChanPostContent postContent) {
		this.content = postContent;
	}

	@Override
	public String toString() {
		return "Post [number=" + number + ", timeStamp=" + timeStamp + ", comentary=" + comentary + "]";
	}

}
