package com.teles.chanchan.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleThreadResponse {

	@JsonProperty("no")
	private Integer number;

	@JsonProperty("last_modified")
	private long lastModified;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "SimpleThreadResponse [number=" + number + ", lastModified=" + lastModified + "]";
	}

}
