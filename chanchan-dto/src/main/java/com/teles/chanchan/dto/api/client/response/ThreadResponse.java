package com.teles.chanchan.dto.api.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadResponse extends SimpleThreadResponse {

	@JsonProperty("sub")
	private String name;

	@JsonProperty("com")
	private String description;

	@JsonProperty("semantic_url")
	private String semanticUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSemanticUrl() {
		return semanticUrl;
	}

	public void setSemanticUrl(String semanticUrl) {
		this.semanticUrl = semanticUrl;
	}

	@Override
	public String toString() {
		return "ThreadResponse [name=" + name + ", description=" + description + ", semanticUrl=" + semanticUrl
				+ ", getNumber()=" + getNumber() + ", getLastModified()=" + getLastModified() + ", getBoard()="
				+ getBoard() + "]";
	}

}