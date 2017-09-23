package com.teles.chanchan.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.teles.chanchan.domain.settings.ChanchanSettings;

@ComponentScan({ "com.teles.chanchan" })
@Import(value = { ChanchanSettings.class })
@SpringBootApplication
public class ChanchanWebApp {

	public static void main(String[] args) {
		SpringApplication.run(ChanchanWebApp.class, args);
	}

}
