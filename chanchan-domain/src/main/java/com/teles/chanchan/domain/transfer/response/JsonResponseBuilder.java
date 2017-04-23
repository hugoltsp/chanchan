package com.teles.chanchan.domain.transfer.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teles.chanchan.domain.exception.ChanchanException;

public final class JsonResponseBuilder {

	private final Map<String, Object> data;

	private JsonResponseBuilder() {
		this.data = new HashMap<>();
	}

	public static JsonResponseBuilder create() {
		return new JsonResponseBuilder();
	}

	public <T> JsonResponseBuilder withData(String name, T value) {
		this.data.put(name, value);
		return this;
	}

	public JsonResponse build() throws ChanchanException {
		JsonResponse response = null;

		try {

			String json = new ObjectMapper().writeValueAsString(this.data);
			this.data.clear();
			response = new JsonResponse(json);

		} catch (JsonProcessingException e) {
			throw new ChanchanException(e);
		}

		return response;
	}

}