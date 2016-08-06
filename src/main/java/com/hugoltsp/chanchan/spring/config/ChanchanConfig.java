package com.hugoltsp.chanchan.spring.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ChanchanConfig {

	public ChanchanConfig(Environment env){
		String property = env.getProperty("chanchan.catalogseeds");
	}
	
}
