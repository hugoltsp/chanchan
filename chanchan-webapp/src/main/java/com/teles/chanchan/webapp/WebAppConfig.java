package com.teles.chanchan.webapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.teles.chanchan.webapp.endpoint.actuator.ApplicationPropertiesEndpoint;

@SpringBootConfiguration
public class WebAppConfig {

	private final Environment environment;

	public WebAppConfig(Environment environment) {
		this.environment = environment;
	}
	
	@Bean
	public ApplicationPropertiesEndpoint endpoint(){
		return new ApplicationPropertiesEndpoint(environment);
	}
}
