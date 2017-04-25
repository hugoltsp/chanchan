package com.teles.chanchan.webapp.endpoint.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.http.ResponseEntity;

import com.teles.chanchan.domain.transfer.response.JsonResponse;
import com.teles.chanchan.domain.transfer.response.JsonResponseBuilder;
import com.teles.chanchan.domain.util.ResponseEntityUtils;

public class ApplicationPropertiesEndpoint implements Endpoint<ResponseEntity<?>> {

	private final Environment env;

	public ApplicationPropertiesEndpoint(Environment env) {
		this.env = env;
	}

	public String getId() {
		return "app/properties";
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isSensitive() {
		return true;
	}

	@Override
	public ResponseEntity<?> invoke() {
		Map<String, Object> build = this.build();

		JsonResponse jsonResponse = JsonResponseBuilder.create().withData(build).build();
		return ResponseEntityUtils.buildDataResponseEntity(jsonResponse);
	}

	private Map<String, Object> build() {
		Map<String, Object> map = new HashMap<>();

		MutablePropertySources propertySources = ((AbstractEnvironment) env).getPropertySources();

		for (PropertySource<?> propertySource : propertySources) {
			if (propertySource instanceof MapPropertySource && isApplicationConfig(propertySource)) {
				Map<String, Object> source = ((MapPropertySource) propertySource).getSource();
				map.putAll(source);
			}
		}

		return map;
	}

	private static boolean isApplicationConfig(PropertySource<?> source){
		return source.getName().contains("applicationConfig");
	}
	
}
