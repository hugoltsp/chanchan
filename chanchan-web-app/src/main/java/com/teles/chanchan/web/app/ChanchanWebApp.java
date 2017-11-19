package com.teles.chanchan.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("com.teles.chanchan")
@EnableScheduling
@EnableMongoRepositories("com.teles.chanchan.data.repository")
@SpringBootApplication
public class ChanchanWebApp {

	public static void main(String[] args) {
		SpringApplication.run(ChanchanWebApp.class, args);
	}

}
