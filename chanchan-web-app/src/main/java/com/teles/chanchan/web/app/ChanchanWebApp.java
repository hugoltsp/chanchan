package com.teles.chanchan.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("com.teles.chanchan")
@EnableScheduling
@SpringBootApplication
public class ChanchanWebApp {

	public static void main(String[] args) {
		SpringApplication.run(ChanchanWebApp.class, args);
	}

}
