package com.teles.chanchan.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.teles.chanchan.config.datasource.DataSourceConfig;

@ComponentScan("com.teles.chanchan")
@Import(value = { DataSourceConfig.class })
@EnableScheduling
@SpringBootApplication
public class ChanchanSchedulerApp {

	public static void main(String[] args) {
		SpringApplication.run(ChanchanSchedulerApp.class, args);
	}

}
