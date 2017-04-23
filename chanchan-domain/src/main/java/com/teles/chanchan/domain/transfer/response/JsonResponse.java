package com.teles.chanchan.domain.transfer.response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;

public final class JsonResponse {
	
	private final String json;

	JsonResponse(String response) {
		this.json = response;
	}

	@JsonRawValue
	@JsonValue
	public String getJson() {
		return json;
	}

	public String toString() {
		return json;
	}
	
}