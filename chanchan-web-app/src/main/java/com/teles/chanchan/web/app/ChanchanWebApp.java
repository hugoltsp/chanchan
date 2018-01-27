package com.teles.chanchan.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.teles.chanchan")
@SpringBootApplication
public class ChanchanWebApp {

	public static void main(String[] args) {
		SpringApplication.run(ChanchanWebApp.class, args);
	}

}
